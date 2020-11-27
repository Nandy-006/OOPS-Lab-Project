package oops.oops_project.Database;

import android.media.Image;

public class Item {
    //itemname, itemdescription, itemimage, itemqty
    String mName;
    String mDescription;
    int mQuantity;
    Image mImage;

    //item constructor
    public Item (String name, String description, int quantity, Image image) {
        mName = name;
        mDescription = description;
        mQuantity = quantity;
        mImage = image;
    }

    public String getName() {return mName;}

    public String getDescription() {return mDescription;}

    public int getQuantity() {return mQuantity;}

    public Image getImage() {return mImage;}

    //function which changes qty


}
