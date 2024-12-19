package edu.upc.projecte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {

    private List<FAQ> faqList;

    public FAQAdapter(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        FAQ faq = faqList.get(position);
        holder.questionTextView.setText(faq.getQuestion());
        holder.answerTextView.setText(faq.getAnswer());
        holder.senderTextView.setText(faq.getSender());
        holder.dateTextView.setText(faq.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public static class FAQViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;
        TextView senderTextView;
        TextView dateTextView;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
