package com.example.ekzhu.inventoryapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekzhu.inventoryapp.data.InventoryContract.InventoryEntry;





/**
 * Created by ekzhu on 17.07.2017.
 */

public class InventoryCursorAdapter extends CursorAdapter {

    private static final String TAG = InventoryCursorAdapter.class.getSimpleName();



    protected InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    public static class ProductViewHolder {
        TextView product_name;
        TextView product_quantity;
        TextView product_price;
        TextView product_sold;
        ImageView product_add_btn;

        public ProductViewHolder(View itemView) {
            product_name = (TextView) itemView.findViewById(R.id.inventory_item_name_text);
            product_quantity = (TextView) itemView.findViewById(R.id.text_product_current_stock);
            product_price = (TextView) itemView.findViewById(R.id.text_product_price);
            product_sold = (TextView) itemView.findViewById(R.id.text_product_current_sold);
            product_add_btn = (ImageView) itemView.findViewById(R.id.button_sale);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inventory_item, parent ,false);
        ProductViewHolder holder = new ProductViewHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ProductViewHolder holder = (ProductViewHolder)view.getTag();

        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_PRICE);
        int salesColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_ITEMS_SOLD);

        int id = cursor.getInt(cursor.getColumnIndex(InventoryEntry._ID));
        final String productName = cursor.getString(nameColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);
        final int products_sold = cursor.getInt(salesColumnIndex);
        String productPrice = "Price $" + cursor.getString(priceColumnIndex);

        String productQuantity = String.valueOf(quantity) + " Inventory";
        String productSold = String.valueOf(products_sold) + " Sold";

        final Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);


        holder.product_name.setText(productName);
        holder.product_quantity.setText(productQuantity);
        holder.product_price.setText(productPrice);
        holder.product_sold.setText(productSold);


        holder.product_add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ContentResolver resolver = view.getContext().getContentResolver();
                ContentValues values = new ContentValues();
                if (quantity > 0) {
                    int qq = quantity;
                    int yy = products_sold;
                    values.put(InventoryEntry.COL_QUANTITY, --qq);
                    values.put(InventoryEntry.COL_ITEMS_SOLD, ++yy);
                    resolver.update(
                            currentProductUri,
                            values,
                            null,
                            null
                    );
                    context.getContentResolver().notifyChange(currentProductUri, null);
                } else {
                    Toast.makeText(context, "Item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

