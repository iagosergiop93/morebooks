package com.morebooks.morebooks.utils;

import com.morebooks.morebooks.clients.domain.Volume;
import com.morebooks.morebooks.entities.Book;

public class VolumeToBookMapper {

    public static Book volumeToBook(Volume volume) {
        Book book = new Book();
        book.setId(volume.getId());
        book.setAuthors(volume.getVolumeInfo().getAuthors());

        String description = volume.getVolumeInfo().getDescription().substring(0,1023);
        book.setDescription(description);

        book.setPageCount(volume.getVolumeInfo().getPageCount());
        book.setPublisher(volume.getVolumeInfo().getPublisher());
        book.setPublishedDate(volume.getVolumeInfo().getPublishedDate());
        book.setSubtitle(volume.getVolumeInfo().getSubtitle());
        book.setTitle(volume.getVolumeInfo().getTitle());
        book.setThumbnail(volume.getVolumeInfo().getImageLinks().getThumbnail());

        return book;
    }

}
