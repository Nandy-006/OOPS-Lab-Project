package oops.oops_project.FirestoreDatabase;

import oops.oops_project.R;

public class Item
{
    private String title, descriprition;
    private int image = R.drawable.item;
    private int quantity;

    public Item() {}

    public Item(String title, String descriprition, int quantity)
    {
        this.title = title;
        this.descriprition = descriprition;
        this.quantity = quantity;
    }

    public Item(String title, String descriprition, int image, int quantity)
    {
        this.title = title;
        this.descriprition = descriprition;
        this.image = image;
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity;}
    public void setQuantity(int quantity) { this.quantity = quantity;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getDescriprition() { return descriprition;}
    public void setDescriprition(String descriprition) { this.descriprition = descriprition;}

    public int getImage() { return image;}
    public void setImage(int image) { this.image = image;}
}