package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUsersMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public HashMap<Group, List<User>> getGroupUserMap() {
        return groupUsersMap;
    }

    public void setGroupUserMap(HashMap<Group, List<User>> groupUserMap) {
        this.groupUsersMap = groupUserMap;
    }

    public HashMap<Group, List<Message>> getGroupMessageMap() {
        return groupMessageMap;
    }

    public void setGroupMessageMap(HashMap<Group, List<Message>> groupMessageMap) {
        this.groupMessageMap = groupMessageMap;
    }

    public HashMap<Message, User> getSenderMap() {
        return senderMap;
    }

    public void setSenderMap(HashMap<Message, User> senderMap) {
        this.senderMap = senderMap;
    }

    public HashMap<Group, User> getAdminMap() {
        return adminMap;
    }

    public void setAdminMap(HashMap<Group, User> adminMap) {
        this.adminMap = adminMap;
    }

    public HashSet<String> getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(HashSet<String> userMobile) {
        this.userMobile = userMobile;
    }

    public int getCustomGroupCount() {
        return customGroupCount;
    }

    public void setCustomGroupCount(int customGroupCount) {
        this.customGroupCount = customGroupCount;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUsersMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }
    public String createUser(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile))
        {
            throw new Exception("User already exist");
        }
        User user=new User(name,mobile);
        userMobile.add(mobile);
        return "user successfully created";
    }
    public Group createGroup(List<User> users){

        if(users.size()<2)
        {
            return null;
        }
            User admin=users.get(0);
            User user2=users.get(2);
            String name="";
            if(users.size()==2)
            {
                name=user2.getName();
            }
            else
            {
                customGroupCount++;

                name =Integer.toString(customGroupCount);
            }
            Group group=new Group(name,users.size());
            if(name.equals("Group count"))
            {
                groupUsersMap.put(group,users);
            }
            adminMap.put(group,admin);
            groupMessageMap.put(group,new ArrayList<>());

            return  group;
        }

    public int createMessage(String content){
        Message message=new Message(messageId++,content);

        return messageId;
    }
    public int sendMessage(Message message, User sender, Group group) throws Exception{

        if((!groupUsersMap.containsKey(group))||(!groupUsersMap.get(group).contains(sender)))
        {
            throw new Exception("group does not exist or sender does not exist in group");
        }

            List<Message>ml=groupMessageMap.get(group);
            ml.add(message);
            groupMessageMap.put(group,ml);
            return ml.size();

    }
    public String changeAdmin(User approver, User user, Group group) throws Exception{

        if(!groupUsersMap.containsKey(group)||!approver.equals(adminMap.get(group))||(!groupUsersMap.get(group).contains(user)))
        {
            throw new Exception("throwing exception for may be group does not exist or approver is not admin or user is not a member of group");
        }
        adminMap.put(group,user);

        return "SUCCESS";
    }


}
