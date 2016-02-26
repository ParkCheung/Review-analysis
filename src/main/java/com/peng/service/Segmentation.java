package com.peng.service;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2016/2/26.
 */
public class Segmentation {

    /**
     * ���ļ����зִ�
     * @param fileName
     * @return
     */
    public Map<Integer, Map<String, Integer>> splitCommentsInFile(String fileName) {
        //�ִʲ���
        BufferedReader reader;
        Map<String, Integer> wordsFrenMaps = new HashMap<>();
        Map<Integer, Map<String, Integer>> splitCommentMap = new HashMap<>();
        IKSegmentation ikSegmenter;
        Lexeme lexeme;
        String line;
        try {
            int i = 0;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            while ((line = reader.readLine()) != null) {
                ikSegmenter = new IKSegmentation(new StringReader(line), true);
                while ((lexeme = ikSegmenter.next()) != null) {
                    if (lexeme.getLexemeText().length() <= 1) {
                        continue;
                    }
                    if (wordsFrenMaps.containsKey(lexeme.getLexemeText())) {
                        wordsFrenMaps.put(lexeme.getLexemeText(), wordsFrenMaps.get(lexeme.getLexemeText()) + 1);
                    } else {
                        wordsFrenMaps.put(lexeme.getLexemeText(), 1);
                    }
                }
                splitCommentMap.put(i, wordsFrenMaps);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitCommentMap;
    }

    /**
     * ���д�Ƶͳ������
     *
     * @param wordsFrenMaps
     * @return
     */
    public List<Map.Entry<String, Integer>> sortSegmentResult(Map<String, Integer> wordsFrenMaps) {
        //���ｫmap.entrySet()ת����list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordsFrenMaps.entrySet());
        //Ȼ��ͨ���Ƚ�����ʵ������
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //��������
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }


}
