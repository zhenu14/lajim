package com.ssm.mutithread;

class Book {
    private String bookname;
    private String price;
    private String author;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString(){
        return  "书名："+this.getBookname()+" 价格："+this.getPrice()+" 作者："+this.getAuthor();
    }
}

public class Demo {

    public static void main(String[] args){
        Book book = new Book();
        book.setBookname("小王子");
        book.setPrice("35.5");
        book.setAuthor("aaaa");
        System.out.println(book);

    }

}
