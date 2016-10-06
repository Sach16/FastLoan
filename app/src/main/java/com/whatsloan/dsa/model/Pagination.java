package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination implements Parcelable{

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("links")
    @Expose
    private LinksPage links;

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The perPage
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     *
     * @param perPage
     * The per_page
     */
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     *
     * @return
     * The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     * The current_page
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The links
     */
    public LinksPage getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    public void setLinks(LinksPage links) {
        this.links = links;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.count);
        dest.writeInt(this.perPage);
        dest.writeInt(this.currentPage);
        dest.writeInt(this.totalPages);
        dest.writeParcelable(this.links, 0);
    }

    public Pagination() {}

    protected Pagination(Parcel in) {
        this.total = in.readInt();
        this.count = in.readInt();
        this.perPage = in.readInt();
        this.currentPage = in.readInt();
        this.totalPages = in.readInt();
        this.links = in.readParcelable(LinksPage.class.getClassLoader());

    }

    public static final Creator<Pagination> CREATOR = new Creator<Pagination>() {
        public Pagination createFromParcel(Parcel source) {
            return new Pagination(source);
        }

        public Pagination[] newArray(int size) {
            return new Pagination[size];
        }
    };
}
