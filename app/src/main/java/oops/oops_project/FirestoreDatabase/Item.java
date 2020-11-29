package oops.oops_project.FirestoreDatabase;

import oops.oops_project.R;

public class Item
{
    public static final int DEFAULT_ITEM = R.drawable.item;

    private String title, description;
    private String imageUrl;
    private int quantity;

    public Item() {}

    public Item(String title, String description, String imageUrl, int quantity)
    {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity;}
    public void setQuantity(int quantity) { this.quantity = quantity;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getDescription() { return description;}
    public void setDescription(String descriprition) { this.description = descriprition;}

    public String getImage() { return imageUrl;}
    public void setImage(String imageUrl) { this.imageUrl = imageUrl;}
}
