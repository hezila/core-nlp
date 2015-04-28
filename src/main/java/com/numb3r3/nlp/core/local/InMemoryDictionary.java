package com.numb3r3.nlp.core.local;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.numb3r3.common.math.stat.Frequency;
import com.numb3r3.nlp.core.Dictionary;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class InMemoryDictionary implements Dictionary {

    BiMap<String, Integer> dict = HashBiMap.create();
    BiMap<Integer, String> inverse_dict = dict.inverse();

    //Map<Integer, Double> freq = Maps.newHashMap();

    Frequency<Integer> frequency = new Frequency<Integer>();

    @Override
    public int word(String term) {
        if (dict.containsKey(term)) {
            return dict.get(term);
        }
        return -1;
    }

    @Override
    public String term(int index) {
        if (inverse_dict.containsKey(index)) {
            return inverse_dict.get(index);
        }
        return null;
    }

    @Override
    public int add(String term) {
        int word = word(term);
        if (word >= 0) {
            frequency.add(word);
        } else {
            word = dict.size();
            dict.put(term, word);
            frequency.add(word);
        }
        return word;
    }

    @Override
    /**
     * TODO
     */
    public int remove(String term) {
        throw new UnsupportedOperationException("remove() not supported on "
                + this.getClass());
    }

    @Override
    /**
     * TODO
     */
    public int remove(int word) {
        throw new UnsupportedOperationException("remove() not supported on "
                + this.getClass());
    }

    @Override
    public int size() {
        return dict.size();
    }

    @Override
    public void reset() {
        dict.clear();
        frequency = new Frequency<Integer>();
    }

    @Override
    public double freq(final int word) {
        if (word > size() - 1) {
            throw new IllegalArgumentException("out of bound index "
                    + this.getClass());
        }
        return frequency.getCounts(word);
    }

    @Override
    public int counts(final int word) {
        if (word > this.size() - 1) {
            throw new IllegalArgumentException("out of bound index" + this.getClass());
        }
        return frequency.getCounts(word);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean has(String term) {
        return dict.containsKey(term);
    }

    @Override
    public boolean has(String term, double min) {
        int word = dict.get(term);
        if (word >= 0) {
            return frequency.gte(word, min);
        }
        return false;
    }

    @Override
    public void dumps(String targetFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(targetFile), "UTF-8"));

            Iterator<String> it = dict.keySet().iterator();
            while (it.hasNext()) {
                String term = it.next();
                int word = word(term);
                double freq = freq(word);
                writer.write(term + "\t" + word + "\t" + Double.toString(freq) + "\n");
            }
            writer.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void loads(String sourceFile) {
        dict.clear();
        frequency = new Frequency<Integer>();
//        frequency.clear();
        try {
            List<String> lines = Files.readLines(new File(sourceFile), Charsets.UTF_8);
            for (String line : lines) {
                Iterable<String> tokens = Splitter.on('\t').trimResults().split(line);
                List<String> tokens_temp = Lists.newArrayList(tokens);
                String term = tokens_temp.get(0);
                int word = Integer.parseInt(tokens_temp.get(1));
                double freq_value = Double.parseDouble(tokens_temp.get(2));
                dict.put(term, word);
                frequency.add(word, freq_value);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> terms() {
        return this.dict.keySet();
    }

    @Override
    public void profile() {
        System.out.println("Words Num: " + this.size());

    }

    @Override
    public void addterm(String term, double count) {

        int word = this.add(term);
        frequency.add(word, count);


    }

    @Override
    public Dictionary trim(double min) {
        Dictionary newdict = new InMemoryDictionary();

        for (int word : this.frequency.gte_container(min, 0)) {
            String term = this.term(word);
            newdict.addterm(term, this.counts(word));
        }

        return newdict;
    }

    @Override
    public void addAll(Iterable<String> collection) {
        for (String term : collection) {
            this.add(term);
        }
    }

}
