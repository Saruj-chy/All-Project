package com.example.allproject.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allproject.Activity.FragmentActivity;
import com.example.allproject.Class.Category;
import com.example.allproject.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MainPageViewHolder>
{
    private Context mCtx;
    private List<Category> toolsList;

    public RecyclerViewAdapter(Context mCtx, List<Category> toolsList) {
        this.mCtx = mCtx;
        this.toolsList = toolsList;
    }



    @NonNull
    @Override
    public MainPageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_card_list, null);
        return new MainPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainPageViewHolder holder, final int position) {

        Category category = toolsList.get(position);
        holder.textViewName.setText(category.getTitle());
        holder.logoImage.setImageResource(category.getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Toast.makeText(context, "position: "+ position, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, FragmentActivity.class);
                    context.startActivity(intent);


                }
            });

//        if(position==0) {
//            holder.textViewName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, Faculty_MainActivity.class);
////                    intent.putExtra("name", Name);
//                    context.startActivity(intent);
//                }
//            });
//        }
//        else if(position==1) {
//            holder.textViewName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, AllUnitActivity.class);
////                    intent.putExtra("name", Name);
//                    context.startActivity(intent);
//                }
//            });
//        }
//
//        else if(position==2) {
//            holder.textViewName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, ChoiceActivity.class);
////                    intent.putExtra("name", Name);
//                    context.startActivity(intent);
//                }
//            });
//        }
//        else if(position==3) {
//            holder.textViewName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, DepartmentPostActivity.class);
////                    intent.putExtra("name", Name);
//                    context.startActivity(intent);
//                }
//            });
//        }
//
//        else if(position==4) {
//            holder.textViewName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View view) {
//                    CharSequence options[] = new CharSequence[]
//                            {
//                                    "Practice Test",
//                                    "Model Test"
//                            };
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
//                    builder.setTitle("Select Any Test?") ;
//
//                    builder.setItems(options, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            if( i == 0){
//                                Context context = view.getContext();
//                                Intent intent = new Intent(context, PracticeTestActivity.class);
//                                context.startActivity(intent);
//                            }
//                            else if( i == 1){
//                                Context context = view.getContext();
//                                Intent intent = new Intent(context, ModelQuestionMainActivity.class);
//                                context.startActivity(intent);
//                            }
//                        }
//                    });
//                    builder.show() ;
//                }
//            });
//
//
//
//
//
//        }
//        else if(position==5) {
//            holder.textViewName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, NoticeListActivity.class);
////                    intent.putExtra("name", Name);
//                    context.startActivity(intent);
////                    Toast.makeText(mCtx, "Upcoming soon...", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


    }

    @Override
    public int getItemCount() {
        return toolsList.size();
    }


    class MainPageViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewName;
        ImageView logoImage ;




        public MainPageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.cardtextview);
            logoImage = itemView.findViewById(R.id.logo_image);



        }
    }




}