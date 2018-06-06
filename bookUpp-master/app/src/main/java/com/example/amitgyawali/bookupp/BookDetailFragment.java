package com.example.amitgyawali.bookupp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.amitgyawali.bookupp.Tools.BookInformation;
import com.example.amitgyawali.bookupp.Tools.ImageFetcher;

public class BookDetailFragment extends Fragment {
    private boolean isTextViewClicked = false;
    private BookInformation book;
    private View parentView;


    public BookDetailFragment() {
        // Required empty public constructor
    }

    public static BookDetailFragment newInstance(BookInformation book){
        BookDetailFragment bookFrag = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("bookInfo", book);
        bookFrag.setArguments(args);
        return bookFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_book_detail, container, false);
        Bundle bundle = getArguments();
        if(bundle == null){
            return parentView;
        }
        book = (BookInformation) bundle.getSerializable("bookInfo");
        setUIFields();
        return parentView;
    }

    private void setUIFields(){
        //Image first to give more time to possibly retrieve online.
        setImage();
        setTitle();
        setLocation();
        setRatingBar();
        setDate();
        setISBN();
        setDescription();
        setPageCount();
        setNumRatings();
    }

    private void setImage(){
        ImageView icon = (ImageView) parentView.findViewById(R.id.bookdetail_book_image);
        ImageFetcher.loadImage(book.imageURL, icon);
    }

    private void setTitle(){
        TextView title = (TextView) parentView.findViewById(R.id.bookdetail_book_title);
        title.setText(book.title);
    }

    private void setLocation(){
        TextView locationEdit = (TextView) parentView.findViewById(R.id.bookdetail_location);
        locationEdit.setText("Location: " + book.location);
    }

    private void setRatingBar(){
        RatingBar ratingBar = (RatingBar) parentView.findViewById(R.id.bookdetail_book_rating_bar);
        DrawableCompat.setTint(ratingBar.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.ratingBar));
        ratingBar.setRating((float) book.averageRating);
    }

    private void setDate(){
        TextView date = (TextView) parentView.findViewById(R.id.bookdetail_scan_date);
        date.setText("Last Updated: " + book.timeLastUpdated);
    }
    private void setISBN(){
        TextView isbn = (TextView) parentView.findViewById(R.id.bookdetail_isbn);
        isbn.setText("ISBN: " + book.isbn);
    }

    private void setDescription(){
        TextView description = (TextView) parentView.findViewById(R.id.bookdetail_book_description);
        description.setText(book.description);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTextViewClicked){
                    ((TextView) v).setMaxLines(3);
                    isTextViewClicked = false;
                } else {
                    ((TextView) v).setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                }
            }
        });
    }
    private void setPageCount(){
        TextView pageCount = (TextView) parentView.findViewById(R.id.bookdetail_pages);
        pageCount.setText(String.valueOf(book.pageCount) + " pages");
    }

    private void setNumRatings(){
        TextView numRatings = (TextView) parentView.findViewById(R.id.bookdetail_book_rating);
        numRatings.setText(String.valueOf(book.averageRating) + "/5.0 (" + String.valueOf(book.ratingsCount) + " reviews)");
    }
}
