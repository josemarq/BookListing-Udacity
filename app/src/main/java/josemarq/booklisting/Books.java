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

/**
 * An {@link Books} object contains information related to a single book.
 */
public class Books {

    private String mTitulo;
    private String mAutor;
    private String mPublisher;

    /**
     * Website URL of the book
     */
    private String mUrl;

    /**
     * Constructs a new {@link Books} object.
     *
     * @param titulo    is the title of the book
     * @param autor     is the author of the boook
     * @param publisher is the publisher or the book
     * @param url       is the website URL to find more details about the book
     */
    public Books(String titulo, String autor, String publisher, String url) {
        mTitulo = titulo;
        mAutor = autor;
        mPublisher = publisher;
        mUrl = url;
    }

    /**
     * Returns the items
     */
    public String getTitulo() {
        return mTitulo;
    }

    public String getAutor() {
        return mAutor;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getUrl() {
        return mUrl;
    }
}