package com.example.demo.threads.lock;

public class FileMock {

    private String content[];
    private int index;

    public FileMock(int size, int length){
        content=new String[size];
        for (int i=0; i<size; i++){
            StringBuilder buffer=new StringBuilder(length);
            for (int j=0; j<length; j++){
                int indice=(int)(Math.random()*255);
                buffer.append((char)indice);
            }
            content[i]=buffer.toString();
        }
        index=0;
    }

    public boolean hasMoreLines(){
        return index<content.length;
    }

    public String getLine(){
        if (this.hasMoreLines()) {
            System.out.println("Mock: "+(content.length-index));
            return content[index++];
        }
        return null;
    }

    public static void main(String[] args) {
        /*for (int j=0; j<10; j++){
            int indice=(int)(Math.random()*255);
            System.out.println(String.format("%s:%d",(char)indice,indice));
        }*/
        FileMock fm = new FileMock(2, 5);
        System.out.println(fm.getLine());
        System.out.println(fm.hasMoreLines());
        System.out.println(fm.getLine());
        System.out.println(fm.hasMoreLines());
    }
}
