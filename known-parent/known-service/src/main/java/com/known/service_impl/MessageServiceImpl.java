package com.known.service_impl;

import com.known.common.enums.ArticleTypeEnum;
import com.known.common.enums.MessageTypeEnum;
import com.known.common.enums.PageSizeEnum;
import com.known.common.enums.TextLengthEnum;
import com.known.common.model.*;
import com.known.common.utils.Constants;
import com.known.common.utils.UUIDUtil;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.*;
import com.known.manager.query.*;
import com.known.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper<Message, MessageQuery> messageMapper;

    @Autowired
    private TopicMapper<Topic, TopicQuery> topicMapper;

    @Autowired
    private com.known.manager.mapper.KnowledgeMapper<Knowledge, KnowledgeQuery> KnowledgeMapper;

    @Autowired
    private AskMapper<Ask, AskQuery> askMapper;

    @Autowired
    private ShuoShuoMapper<ShuoShuo, ShuoShuoQuery> shuoShuoMapper;

    public void createMessage(MessageParams messageParams) {
        MessageTypeEnum type = messageParams.getMessageType();
        switch (type) {
            case AT_ARTICLE_MESSAGE:
                atMessage(messageParams);
                break;
            case COMMENT_MESSAGE:
                commentMessage(messageParams);
                break;
            case ADOPT_ANSWER:
                adoptAnswerMessage(messageParams);
                break;
            case SYSTEM_MARK:
                changeMarkMessage(messageParams);
            default:
                break;
        }

    }

    private void changeMarkMessage(MessageParams messageParams) {
        List<Message> messageList = new ArrayList<>();
        Set<String> receiveUserIds = messageParams.getReceiveUserIds();
        Date curDate = new Date();
        Message message = null;
        for (String receivedUserId : receiveUserIds) {
            message = new Message();
            message.setId(UUIDUtil.getUUID());
            message.setReceivedUserId(receivedUserId);
            message.setUrl(Constants.DOMAIN + "/userAdmin/messageList");
            message.setDescription("<span>系统消息</span>" +
                    "【" + messageParams.getDes() + "】");
            message.setCreateTime(curDate);
            messageList.add(message);
        }
        if (!messageList.isEmpty()) {
            messageMapper.insertBatch(messageList);
        }
    }

    private void adoptAnswerMessage(MessageParams messageParams) {
        List<Message> messageList = new ArrayList<>();
        Set<String> receiveUserIds = messageParams.getReceiveUserIds();
        AskQuery askQuery = new AskQuery();
        askQuery.setAskId(messageParams.getArticleId());
        Ask ask = askMapper.selectList(askQuery).get(0);
        String title = ask.getTitle();
        Message message = null;
        removeUser(receiveUserIds, messageParams.getSendUserId());
        Date curDate = new Date();
        for (String receivedUserId : receiveUserIds) {
            message = new Message();
            message.setId(UUIDUtil.getUUID());
            message.setReceivedUserId(receivedUserId);
            message.setUrl(getUrl4CommentAndAt(messageParams));
            message.setDescription("<span>" + messageParams.getSendUserName() + "</span>" +
                    "在【" + messageParams.getArticleType().getDesc() + "】" + "(" + title + ")中采纳了你的答案");
            message.setCreateTime(curDate);
            messageList.add(message);
        }
        if (!messageList.isEmpty()) {
            messageMapper.insertBatch(messageList);
        }

    }

    private void commentMessage(MessageParams messageParams) {
        List<Message> messageList = new ArrayList<>();
        Set<String> receiveUserIds = messageParams.getReceiveUserIds();
        ArticleTypeEnum articleType = messageParams.getArticleType();
        String articleId = messageParams.getArticleId();
        String title = "";
        if (articleType == ArticleTypeEnum.TOPIC) {
            TopicQuery topicQuery = new TopicQuery();
            topicQuery.setTopicId(articleId);
            Topic topic = topicMapper.selectList(topicQuery).get(0);
            title = topic.getTitle();
        } else if (articleType == ArticleTypeEnum.KNOWLEDGE) {
            KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
            knowledgeQuery.setKnowledgeId(articleId);
            Knowledge knowledge = KnowledgeMapper.selectList(knowledgeQuery).get(0);
            title = knowledge.getTitle();
        } else if (articleType == ArticleTypeEnum.Ask) {
            AskQuery askQuery = new AskQuery();
            askQuery.setAskId(articleId);
            Ask ask = askMapper.selectList(askQuery).get(0);
            title = ask.getTitle();
        } else {
            ShuoShuoQuery shuoShuoQuery = new ShuoShuoQuery();
            shuoShuoQuery.setId(articleId);
            ShuoShuo shuoShuo = shuoShuoMapper.selectList(shuoShuoQuery).get(0);
            title = shuoShuo.getContent();
            if (title.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()) {
                title = title.substring(0,
                        (int) TextLengthEnum.TEXT_200_LENGTH.getLength())
                        + "......";
            }
        }

        Message message = null;
        removeUser(receiveUserIds, messageParams.getSendUserId());
        Date curDate = new Date();
        for (String receivedUserId : receiveUserIds) {
            message = new Message();
            message.setId(UUIDUtil.getUUID());
            message.setReceivedUserId(receivedUserId);
            message.setUrl(getUrl4CommentAndAt(messageParams));
            message.setDescription("<span>" + messageParams.getSendUserName() + "</span>" +
                    "在【" + messageParams.getArticleType().getDesc() + "】" + "&nbsp;&nbsp;(" + title + ")中回复了你");
            message.setCreateTime(curDate);
            messageList.add(message);
        }
        if (!messageList.isEmpty()) {
            messageMapper.insertBatch(messageList);
        }

    }

    private void atMessage(MessageParams messageParams) {
        List<Message> messageList = new ArrayList<>();
        Set<String> receiveUserIds = messageParams.getReceiveUserIds();
        Message message = null;
        removeUser(receiveUserIds, messageParams.getSendUserId());
        Date curDate = new Date();
        for (String receivedUserId : receiveUserIds) {
            message = new Message();
            message.setId(UUIDUtil.getUUID());
            message.setReceivedUserId(receivedUserId);
            message.setUrl(getUrl4CommentAndAt(messageParams));
            message.setDescription("<span>" + messageParams.getSendUserName() + "</span>" +
                    "在【" + messageParams.getArticleType().getDesc() + "】" + "中提到了你");
            message.setCreateTime(curDate);
            messageList.add(message);
        }
        if (!messageList.isEmpty()) {
            messageMapper.insertBatch(messageList);
        }
    }

    private void removeUser(Set<String> receiveUserIds, String sendUserId) {
        Iterator<String> iterator = receiveUserIds.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(sendUserId)) {
                iterator.remove();
            }
        }
    }

    public Message getMessageById(String id, String userId) {
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setReceivedUserId(userId);
        messageQuery.setId(id);
        List<Message> list = messageMapper.selectList(messageQuery);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public PageResult<Message> findMessageByPage(MessageQuery messageQuery) {
        int pageSize = PageSizeEnum.PAGE_SIZE20.getSize();
        int count = findMessageCount(messageQuery);
        int pageNum = messageQuery.getPageNum() == 1 ? 1 : messageQuery.getPageNum();
        Page page = new Page(pageNum, count, pageSize);
        messageQuery.setPage(page);
        List<Message> list = messageMapper.selectList(messageQuery);
        return new PageResult<>(page, list);
    }

    public int findMessageCount(MessageQuery messageQuery) {
        return messageMapper.selectCount(messageQuery).intValue();
    }

    public void update(String[] ids, String userId) throws BussinessException {
        if (ids == null || userId == null || ids.length == 0) {
            throw new BussinessException("参数错误");
        }
        messageMapper.update(userId, ids);
    }

    public void delMessage(String userId, String[] ids)
            throws BussinessException {
        if (ids == null || userId == null || ids.length == 0) {
            throw new BussinessException("参数错误");
        }
        messageMapper.delete(userId, ids);

    }


    private String getUrl4CommentAndAt(MessageParams params) {
        String location = "";
        if (params.getPageNum() != null) {
            location = "#" + params.getPageNum() + "_" + params.getCommentId();
        }
        switch (params.getArticleType()) {
            case TOPIC:
                return  "/topic/" + params.getArticleId() + location;
            case Ask:
                return  "/ask/" + params.getArticleId() + location;
            case KNOWLEDGE:
                return "/knowledge/" + params.getArticleId() + location;
            case SHUOSHUO:
                return  "/userCenter/" + params.getArticleUserId() ;
        }
        return null;
    }
}
