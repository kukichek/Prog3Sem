import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class View extends JFrame {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML Files", "xml");

    private final int WINDOW_WIDTH = 640;
    private final int WINDOW_HEIGHT = 480;

    private final int PANEL_WIDTH = 620;
    private final int PANEL_HEIGHT = 420;

    List<AbstractLamp> list = new ArrayList<AbstractLamp>();

    JTextArea sortedWithProducerArea = new JTextArea();
    JTextArea qualityArea = new JTextArea();
    JTextArea producersArea = new JTextArea();

    JTextArea producerPriceRangeArea = new JTextArea();

    private Set<String> producers = new HashSet<>();
    private Map<String, List<Double>> priceRange = new HashMap<>();

    public View() {
        super("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(550, 60, WINDOW_WIDTH, WINDOW_HEIGHT);

        setFilters();

        JTabbedPane finalPanel = new JTabbedPane();
        finalPanel.addTab("2: Подешевле", initPanelWithTextArea(sortedWithProducerArea));
        finalPanel.addTab("3: По качеству", initPanelWithTextArea(qualityArea));
        finalPanel.addTab("4: Производители", initPanelWithTextArea(producersArea));
//
        finalPanel.addTab("6: Диапазон", initPanelWithTextArea(producerPriceRangeArea));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(initOpenMenu());
        menuBar.add(initDataMenu());

        setJMenuBar(menuBar);

        add(finalPanel);
    }

    private JMenu initOpenMenu() {
        JMenu menu = new JMenu("Файл");

        JMenuItem openMenuItem = new JMenuItem("Открыть");

        menu.add(openMenuItem);

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try{
                        list = DomReader.read(fileChooser.getSelectedFile());
                        JOptionPane.showMessageDialog(null, "Amount of lamps: " + list.size());
                    }
                    catch (DataException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    catch (IOException | SAXException | ParserConfigurationException exception) {
                        JOptionPane.showMessageDialog(null, "Parsing failure");
                    }
                }
            }
        });

        return menu;
    }

    private JMenu initDataMenu() {
        JMenu menu = new JMenu("Данные");

        JMenuItem lowerPriceMenuItem = new JMenuItem("Подешевле");
        JMenuItem qualityMenuItem = new JMenuItem("По качеству");
        JMenuItem producersMenuItem = new JMenuItem("Производители");
        JMenuItem searchMenuItem = new JMenuItem("Поиск");
        JMenuItem rangeMenuItem = new JMenuItem("Диапазон");

        menu.add(lowerPriceMenuItem);
        menu.add(qualityMenuItem);
        menu.add(producersMenuItem);

        menu.add(rangeMenuItem);

        lowerPriceMenuItem.addActionListener(new ActionListener() {
            Comparator<AbstractLamp> nameComparator = Comparator.comparing(AbstractLamp::countPrice);

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String currentProducer = JOptionPane.showInputDialog(null,
                        "Введите производителя",
                        "Введите производителя", JOptionPane.QUESTION_MESSAGE);

                if (currentProducer != null) {
                    if (list != null) {
                        setText(list.stream().filter((AbstractLamp lamp) -> {return lamp.getProducer().equals(currentProducer);}).sorted(nameComparator).collect(Collectors.toList()), sortedWithProducerArea);
                    }
                }
            }
        });
        qualityMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setText(list.stream().sorted((AbstractLamp lamp1, AbstractLamp lamp2) -> {double diff2 = lamp2.countPrice() / lamp2.capacity;
                                                                                          double diff1 = lamp1.countPrice() / lamp1.capacity;
                                                                                          return ((Double) diff2).compareTo(diff1);}).collect(Collectors.toList()), qualityArea);
            }
        });
        producersMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getProducers();

                setText(producers, producersArea);
            }
        });

        rangeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                priceRange.clear();
                getProducers();

                Iterator<String> producerIterator = producers.iterator();

                while (producerIterator.hasNext()) {
                    String curProducer = producerIterator.next();
                    double min = list.stream().filter((AbstractLamp lamp) -> {return lamp.getProducer().equals(curProducer);}).mapToDouble(AbstractLamp::countPrice).min().orElse(0);
                    double max = list.stream().filter((AbstractLamp lamp) -> {return lamp.getProducer().equals(curProducer);}).mapToDouble(AbstractLamp::countPrice).max().orElse(0);

                    List<Double> range = new ArrayList<>();
                    range.add(min);
                    range.add(max);

                    priceRange.put(curProducer, range);
                }

                producerPriceRangeArea.setText("");
                Iterator mapIterator = priceRange.entrySet().iterator();

                while(mapIterator.hasNext()) {
                    Map.Entry<String, List<Double>> entry = (Map.Entry<String, List<Double>>) mapIterator.next();
                    producerPriceRangeArea.append(entry.getKey());
                    producerPriceRangeArea.append(": ");
                    producerPriceRangeArea.append(entry.getValue().get(0).toString());
                    producerPriceRangeArea.append(" - ");
                    producerPriceRangeArea.append(entry.getValue().get(1).toString());
                    producerPriceRangeArea.append("\n");
                }
            }
        });

        return menu;
    }

    private void setFilters() {
        fileChooser.setFileFilter(xmlFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    private void setText(Collection collection, JTextArea textArea) {
        textArea.setText("");

        Iterator iterator = collection.iterator();

        while(iterator.hasNext()) {
            textArea.append(iterator.next().toString());
            textArea.append("\n");
        }
    }

    private JPanel initPanelWithTextArea(JTextArea area) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        panel.add(area);

        return panel;
    }

    private Set<String> getProducers() {
        producers.clear();

        if (list != null) {
            producers.addAll(list.stream().map(AbstractLamp::getProducer).collect(Collectors.toList()));
        }

        return producers;
    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
    }
}
