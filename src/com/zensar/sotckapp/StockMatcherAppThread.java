package com.zensar.sotckapp;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class StockMatcherAppThread extends JFrame implements Runnable {
    JTable buySellTable, orderMatcherTable;
    Thread buyThread, sellThread, orderMatchingThread;

    String[][] stockData = {
            {"IBM", "12", "11"},
            {"Reliance", "55", "53"},
            {"TCS", "23", "23"},
            {"Wipro", "17", "15"},
            {"Zensar", "74", "70"}
    };

    String[] stockColumnNames = {"Stock Name", "Buy price", "Sell price"};
    String[] orderMatchColumnNames = {"Stock Name", "Order Price", "Order Match Count"};

    String[][] orderMatchData = {
            {"IBM", "12", "520"},
            {"Reliance", "55", "1250"},
            {"TCS", "23", "487"},
            {"Wipro", "17", "110"},
            {"Zensar", "74", "2350"}
    };

    Map<String, StockLatestData> stockMapForBuySell = new HashMap<>();

    public static int omCounterIBM = 0;
    public static int omCounterRELIENCE = 0;
    public static int omCounterTCS = 0;
    public static int omCounterWIPRO = 0;
    public static int omCounterZENSAR = 0;

    public StockMatcherAppThread() {
        initView();
        initThread();

    }

    // Initialized view
    private void initView() {

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        // Initializing the JTable
        buySellTable = new JTable(stockData, stockColumnNames);
        orderMatcherTable = new JTable(orderMatchData, orderMatchColumnNames);

        JScrollPane spBuySell = new JScrollPane(buySellTable);
        spBuySell.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Stock Buy/Sell Table"));
        JScrollPane spOrderMatcher = new JScrollPane(orderMatcherTable);
        spOrderMatcher
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Order Matcher Table"));
        contentPane.add(spBuySell);
        contentPane.add(spOrderMatcher);

        // init map for separation
        stockMapForBuySell.put("IBM", new StockLatestData());
        stockMapForBuySell.put("Reliance", new StockLatestData());
        stockMapForBuySell.put("TCS", new StockLatestData());
        stockMapForBuySell.put("Wipro", new StockLatestData());
        stockMapForBuySell.put("Zensar", new StockLatestData());
    }

    // Initialized and start thread
    private void initThread() {
        buyThread = new Thread(this);
        buyThread.setName("BUY_STOCK");

        sellThread = new Thread(this);
        sellThread.setName("SELL_STOCK");

        orderMatchingThread = new Thread(this);
        orderMatchingThread.setName("ORDER_MATCHING");

        buyThread.start();
        sellThread.start();
        orderMatchingThread.start();
    }

    public static void main(String[] args) {
        JFrame jframe = new StockMatcherAppThread();
        jframe.setBounds(200, 200, 1000, 300);
        jframe.setTitle("Stock Price App");
        jframe.setVisible(true);
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("BUY_STOCK")) {
            int counter = 0;
            while (true) {

                for (String stock : stockMapForBuySell.keySet()) {

                    StockLatestData stockListForBuySell = stockMapForBuySell.get(stock);
                    List<Integer> buySList = stockListForBuySell.bList;
                    int bPrice = new Random().nextInt(100);
                    buySList.add(bPrice);
                    stockData[counter][1] = bPrice + "";
                    repaint();

                    counter = counter >= 4 ? 0 : ++counter;

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (Thread.currentThread().getName().equals("SELL_STOCK")) {
            int counter = 0;
            while (true) {

                for (String stock : stockMapForBuySell.keySet()) {

                    StockLatestData stockListForBuySell = stockMapForBuySell.get(stock);
                    List<Integer> sellSList = stockListForBuySell.sList;
                    int sPrice = new Random().nextInt(100);
                    sellSList.add(sPrice);
                    stockData[counter][2] = sPrice + "";
                    repaint();

                    counter = counter >= 4 ? 0 : ++counter;

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else if (Thread.currentThread().getName().equals("ORDER_MATCHING")) {
            int counter = 0;
            while (true) {

                List<Integer> recentBuyStockList = new ArrayList<>();
                List<Integer> recentSellStockList = new ArrayList<>();

                for (String stock : stockMapForBuySell.keySet()) {
                    StockLatestData stockListForBuySell = stockMapForBuySell.get(stock);
                    List<Integer> buySList = stockListForBuySell.bList;
                    List<Integer> sellSList = stockListForBuySell.sList;

                    if (buySList.size() > 5 && sellSList.size() > 5) {

                        for (int i = 0; i < 5; i++) {
                            recentBuyStockList.add(buySList.get((buySList.size() - 1) - i));
                            recentSellStockList.add(sellSList.get(sellSList.size() - 1) - i);

                            OptionalDouble buyStockavg = recentBuyStockList.stream()
                                    .mapToInt(number -> number.intValue()).average();

                            OptionalDouble sellStockavg = recentSellStockList.stream()
                                    .mapToInt(number -> number.intValue()).average();

                            if (buyStockavg.getAsDouble() >= sellStockavg.getAsDouble()) {

                                orderMatchData[counter][1] = buyStockavg.getAsDouble() + "";

                                if (stock.equalsIgnoreCase("IBM"))
                                    orderMatchData[counter][2] = String.valueOf(omCounterIBM++);
                                else if (stock.equalsIgnoreCase("Reliance"))
                                    orderMatchData[counter][2] = String.valueOf(omCounterRELIENCE++);
                                else if (stock.equalsIgnoreCase("TCS"))
                                    orderMatchData[counter][2] = String.valueOf(omCounterTCS++);
                                else if (stock.equalsIgnoreCase("Wipro"))
                                    orderMatchData[counter][2] = String.valueOf(omCounterWIPRO++);
                                else if (stock.equalsIgnoreCase("Zensar"))
                                    orderMatchData[counter][2] = String.valueOf(omCounterZENSAR++);
                                repaint();
                            }

                            counter = counter >= 4 ? 0 : ++counter;

                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}