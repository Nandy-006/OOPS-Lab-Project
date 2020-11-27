package oops.oops_project.Activities;

import java.util.ArrayList;

import oops.oops_project.Database.Item;

public class Category {

    private String mName;
    private String mDescription;
    private ArrayList<Item>[] items;

    public void addItem(Item item, int position) {
        items[position].add(item);
    }

    public Category(String name, String description) {
        mName = name;
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    private static int lastCategoryId = 0;

    public static ArrayList<Category> createCategoryList(int numCategories) {
        ArrayList<Category> categories = new ArrayList<Category>();

        for (int i = 1; i <= numCategories; i++) {
            categories.add(new Category("Category " + ++lastCategoryId, "Description "));
        }
        return categories;
    }
}
