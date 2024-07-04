package com.example.chat.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.Message.Message;
import com.example.chat.R;
import com.example.chat.SessionManager.SessionManager;

import java.util.List;
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private List<Message> messageList;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private int senderId; // 当前用户的ID
    private SessionManager sessionManager; // 用于获取当前用户ID的Session管理器
    private String currentUsername;
    private Context context; // 用于初始化SessionManager
  //  public MessageAdapter(List<Message> messageList) {
 //       this.messageList = messageList;
   // }

    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("test",viewType+"");
        int layoutId = (viewType == VIEW_TYPE_MESSAGE_SENT) ?
                R.layout.bg_message_sent: R.layout.bg_message_received;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new MessageViewHolder(view);
    }
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        sessionManager=new SessionManager(context);
        holder.bind(message,sessionManager);
    }
    public MessageAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
        this.sessionManager = new SessionManager(context); // 初始化Session管理器
        // 假设已经有了获取当前用户ID的方法
        this.senderId = sessionManager.getCurrentUserId();
    }
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        // 判断消息是发送的还是接收的
        Log.v("test",senderId+"-"+message.getSenderId());
        return (senderId == message.getSenderId()) ? VIEW_TYPE_MESSAGE_SENT : VIEW_TYPE_MESSAGE_RECEIVED;
    }



    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.text_sent_body);
            if(textViewMessage==null)
                textViewMessage= itemView.findViewById(R.id.text_receive_body);
        }
       //将数据模型（ Message 对象）绑定到 ViewHolder 的视图组件
        public void bind(Message message,SessionManager sessionManager) {
            textViewMessage.setText(message.getContent());
            // 根据消息类型设置背景
            if (message.getSenderId() == sessionManager.getCurrentUserId()) {
                // 我发送的消息
                textViewMessage.setBackgroundResource(R.drawable.avatar_placeholder);
            } else {
                // 接收到的消息
                textViewMessage.setBackgroundResource(R.drawable.background3);
            }

        }
        private int getCurrentUserId() {
            // 使用 SessionManager 或其他方式获取当前用户 ID
            SessionManager sessionManager = new SessionManager(itemView.getContext());  // 传入 itemView 的 Context
            return sessionManager.getCurrentUserId();
        }
    }
}
