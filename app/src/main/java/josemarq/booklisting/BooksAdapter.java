/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package josemarq.booklisting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * An {@link BooksAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Books} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BooksAdapter extends ArrayAdapter<Books> {


    /**
     * Constructs a new {@link BooksAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BooksAdapter(Context context, List<Books> books) {
        super(context, 0, books);
    }


    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.books_list_item, parent, false);
        }

        // Find the books at the given position in the list of books
        Books currentBooks = getItem(position);

        // Get the data
        String titulo = currentBooks.getTitulo();
        String publisher = currentBooks.getPublisher();
        String autor = currentBooks.getAutor();

        // Find the TextView with view Titulo
        TextView tituloView = (TextView) listItemView.findViewById(R.id.titulo);
        // Display the title of the current book in that TextView
        tituloView.setText(titulo);
        // Find the TextView with view Autor
        TextView autorView = (TextView) listItemView.findViewById(R.id.autor);
        // Display the Author of the current book in that TextView
        autorView.setText(autor);
        // Find the TextView with view Publisher
        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        // Display the publisher of the current book in that TextView
        publisherView.setText(publisher);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imagen_libro);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


}