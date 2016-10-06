package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 5/4/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampaignsData {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("promotionals")
    @Expose
    private String promotionals;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("to")
    @Expose
    private To to;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("team")
    @Expose
    private TeamCampaign team;
    @SerializedName("addresses")
    @Expose
    private Addresses addresses;
    @SerializedName("campaignMembers")
    @Expose
    private Members campaignMembers;
    @SerializedName("organizer")
    @Expose
    private String organizer;

    /**
     *
     * @return
     * The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     * The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The promotionals
     */
    public String getPromotionals() {
        return promotionals;
    }

    /**
     *
     * @param promotionals
     * The promotionals
     */
    public void setPromotionals(String promotionals) {
        this.promotionals = promotionals;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The from
     */
    public From getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * The from
     */
    public void setFrom(From from) {
        this.from = from;
    }

    /**
     *
     * @return
     * The to
     */
    public To getTo() {
        return to;
    }

    /**
     *
     * @param to
     * The to
     */
    public void setTo(To to) {
        this.to = to;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The team
     */
    public TeamCampaign getTeam() {
        return team;
    }

    /**
     *
     * @param team
     * The team
     */
    public void setTeam(TeamCampaign team) {
        this.team = team;
    }

    /**
     *
     * @return
     * The addresses
     */
    public Addresses getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     * The addresses
     */
    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public Members getCampaignMembers() {
        return campaignMembers;
    }

    public void setCampaignMembers(Members campaignMembers) {
        this.campaignMembers = campaignMembers;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}