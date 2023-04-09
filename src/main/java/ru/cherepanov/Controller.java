package ru.cherepanov;

import com.arangodb.DbName;
import com.arangodb.entity.BaseDocument;

public class Controller {
    private ImageProcessing processing;

    public String addPerson(String name, int age, String linkToPhoto) {
        String id = "-1";
        processing = new ImageProcessing(linkToPhoto);
        if (processing.isFace()) {
            try (DbUtil db = DbUtil.getInstance()) {
                db.setCurrentDb(DbName.of("mydb"));
                db.setCollection("mycollection");

                BaseDocument doc = new BaseDocument();
                doc.addAttribute("name", name);
                doc.addAttribute("age", age);
                doc.addAttribute("photo", processing.getFilename());
                System.out.println("put in db");
                id = db.insertDocument(doc);
            }
        } else {
            System.out.println("load proper photo");
        }
        return id;
    }

}
