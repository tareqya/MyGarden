package com.example.finalproject.callback;

public interface ImageUrlDownloadListener {
    void onImageUrlDownloaded(String uri);
    void onImageUrlDownloadFailed(String err);
}
