package music.data;
import java.io.*;
import java.util.*;
import music.business.*;
public class ProductIO
{
    private static ArrayList<Product> products = null;

    public static ArrayList<Product> getProducts(String path) {
        ArrayList<Product> products = new ArrayList<>();
        BufferedReader in = null;
        try {
            File file = new File(path);
            in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while (line != null) {
                String[] fields = line.split("\\|");
                String code = fields[0];
                String description = fields[1];
                double price = Double.parseDouble(fields[2]);
                Product product = new Product(code, description, price);
                products.add(product);
                line = in.readLine();
            }
            return products;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Product getProduct(String productCode, String path) {
        ArrayList<Product> products = getProducts(path);
        for (Product product : products) {
            if (product.getCode().equalsIgnoreCase(productCode)) {
                return product;
            }
        }
        return null;
    }

    public static boolean exists(String productCode, String path)
    {
        products = getProducts(path);
        for (Product p : products)
        {
            if (productCode != null &&
                    productCode.equalsIgnoreCase(p.getCode()))
            {
                return true;
            }
        }
        return false;
    }
    private static void saveProducts(ArrayList<Product> products,
                                     String path)
    {
        try
        {
            File file = new File(path);
            PrintWriter out =
                    new PrintWriter(
                            new FileWriter(file));
            for (Product p : products)
            {
                out.println(p.getCode() + "|"
                        + p.getDescription() + "|"
                        + p.getPrice());
            }
            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void insert(Product product, String path)
    {
        products = getProducts(path);
        products.add(product);
        saveProducts(products, path);
    }
    public static void update(Product product, String path)
    {
        products = getProducts(path);
        for (int i = 0; i < products.size(); i++)
        {
            Product p = products.get(i);
            if (product.getCode() != null &&
                    product.getCode().equalsIgnoreCase(p.getCode()))
            {
                products.set(i, product);
            }
        }
        saveProducts(products, path);
    }
    public static void delete(Product product, String path)
    {
        products = getProducts(path);
        for (int i = 0; i < products.size(); i++)
        {
            Product p = products.get(i);
            if (product != null &&
                    product.getCode().equalsIgnoreCase(p.getCode()))
            {
                products.remove(i);
            }
        }
        saveProducts(products, path);
    }
}