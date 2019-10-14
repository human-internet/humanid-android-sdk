package com.humanid.sample.auth.app1.data.source.remote.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStoriesAPIResponse {

    @Expose
    @SerializedName("results")
    private List<Results> mResults;
    @Expose
    @SerializedName("num_results")
    private int mNumResults;
    @Expose
    @SerializedName("last_updated")
    private String mLastUpdated;
    @Expose
    @SerializedName("section")
    private String mSection;
    @Expose
    @SerializedName("copyright")
    private String mCopyright;
    @Expose
    @SerializedName("status")
    private String mStatus;

    public List<Results> getResults() {
        return mResults;
    }

    public void setResults(List<Results> mResults) {
        this.mResults = mResults;
    }

    public int getNumResults() {
        return mNumResults;
    }

    public void setNumResults(int mNumResults) {
        this.mNumResults = mNumResults;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String mLastUpdated) {
        this.mLastUpdated = mLastUpdated;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String mSection) {
        this.mSection = mSection;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String mCopyright) {
        this.mCopyright = mCopyright;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public static class Results {
        @Expose
        @SerializedName("short_url")
        private String mShortUrl;
        @Expose
        @SerializedName("multimedia")
        private List<Multimedia> mMultimedia;
        @Expose
        @SerializedName("geo_facet")
        private List<String> mGeoFacet;
        @Expose
        @SerializedName("per_facet")
        private List<String> mPerFacet;
        @Expose
        @SerializedName("org_facet")
        private List<String> mOrgFacet;
        @Expose
        @SerializedName("des_facet")
        private List<String> mDesFacet;
        @Expose
        @SerializedName("kicker")
        private String mKicker;
        @Expose
        @SerializedName("material_type_facet")
        private String mMaterialTypeFacet;
        @Expose
        @SerializedName("published_date")
        private String mPublishedDate;
        @Expose
        @SerializedName("created_date")
        private String mCreatedDate;
        @Expose
        @SerializedName("updated_date")
        private String mUpdatedDate;
        @Expose
        @SerializedName("item_type")
        private String mItemType;
        @Expose
        @SerializedName("byline")
        private String mByline;
        @Expose
        @SerializedName("url")
        private String mUrl;
        @Expose
        @SerializedName("abstract")
        private String mAbstract;
        @Expose
        @SerializedName("title")
        private String mTitle;
        @Expose
        @SerializedName("subsection")
        private String mSubsection;
        @Expose
        @SerializedName("section")
        private String mSection;

        public String getShortUrl() {
            return mShortUrl;
        }

        public void setShortUrl(String mShortUrl) {
            this.mShortUrl = mShortUrl;
        }

        public List<Multimedia> getMultimedia() {
            return mMultimedia;
        }

        public void setMultimedia(List<Multimedia> mMultimedia) {
            this.mMultimedia = mMultimedia;
        }

        public List<String> getGeoFacet() {
            return mGeoFacet;
        }

        public void setGeoFacet(List<String> mGeoFacet) {
            this.mGeoFacet = mGeoFacet;
        }

        public List<String> getPerFacet() {
            return mPerFacet;
        }

        public void setPerFacet(List<String> mPerFacet) {
            this.mPerFacet = mPerFacet;
        }

        public List<String> getOrgFacet() {
            return mOrgFacet;
        }

        public void setOrgFacet(List<String> mOrgFacet) {
            this.mOrgFacet = mOrgFacet;
        }

        public List<String> getDesFacet() {
            return mDesFacet;
        }

        public void setDesFacet(List<String> mDesFacet) {
            this.mDesFacet = mDesFacet;
        }

        public String getKicker() {
            return mKicker;
        }

        public void setKicker(String mKicker) {
            this.mKicker = mKicker;
        }

        public String getMaterialTypeFacet() {
            return mMaterialTypeFacet;
        }

        public void setMaterialTypeFacet(String mMaterialTypeFacet) {
            this.mMaterialTypeFacet = mMaterialTypeFacet;
        }

        public String getPublishedDate() {
            return mPublishedDate;
        }

        public void setPublishedDate(String mPublishedDate) {
            this.mPublishedDate = mPublishedDate;
        }

        public String getCreatedDate() {
            return mCreatedDate;
        }

        public void setCreatedDate(String mCreatedDate) {
            this.mCreatedDate = mCreatedDate;
        }

        public String getUpdatedDate() {
            return mUpdatedDate;
        }

        public void setUpdatedDate(String mUpdatedDate) {
            this.mUpdatedDate = mUpdatedDate;
        }

        public String getItemType() {
            return mItemType;
        }

        public void setItemType(String mItemType) {
            this.mItemType = mItemType;
        }

        public String getByline() {
            return mByline;
        }

        public void setByline(String mByline) {
            this.mByline = mByline;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public String getAbstract() {
            return mAbstract;
        }

        public void setAbstract(String mAbstract) {
            this.mAbstract = mAbstract;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String getSubsection() {
            return mSubsection;
        }

        public void setSubsection(String mSubsection) {
            this.mSubsection = mSubsection;
        }

        public String getSection() {
            return mSection;
        }

        public void setSection(String mSection) {
            this.mSection = mSection;
        }
    }

    public static class Multimedia {
        @Expose
        @SerializedName("copyright")
        private String mCopyright;
        @Expose
        @SerializedName("caption")
        private String mCaption;
        @Expose
        @SerializedName("subtype")
        private String mSubtype;
        @Expose
        @SerializedName("type")
        private String mType;
        @Expose
        @SerializedName("width")
        private int mWidth;
        @Expose
        @SerializedName("height")
        private int mHeight;
        @Expose
        @SerializedName("format")
        private String mFormat;
        @Expose
        @SerializedName("url")
        private String mUrl;

        public String getCopyright() {
            return mCopyright;
        }

        public void setCopyright(String mCopyright) {
            this.mCopyright = mCopyright;
        }

        public String getCaption() {
            return mCaption;
        }

        public void setCaption(String mCaption) {
            this.mCaption = mCaption;
        }

        public String getSubtype() {
            return mSubtype;
        }

        public void setSubtype(String mSubtype) {
            this.mSubtype = mSubtype;
        }

        public String getType() {
            return mType;
        }

        public void setType(String mType) {
            this.mType = mType;
        }

        public int getWidth() {
            return mWidth;
        }

        public void setWidth(int mWidth) {
            this.mWidth = mWidth;
        }

        public int getHeight() {
            return mHeight;
        }

        public void setHeight(int mHeight) {
            this.mHeight = mHeight;
        }

        public String getFormat() {
            return mFormat;
        }

        public void setFormat(String mFormat) {
            this.mFormat = mFormat;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String mUrl) {
            this.mUrl = mUrl;
        }
    }
}
