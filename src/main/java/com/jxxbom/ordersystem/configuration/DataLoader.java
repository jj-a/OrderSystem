package com.jxxbom.ordersystem.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.jxxbom.ordersystem.util.LoggerFactory;

/**
 * 초기 데이터를 읽는 클래스
 */
@Component
public class DataLoader {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String[]> datas;
    private int index;

    /**
     * 파일을 읽어 데이터를 로드하기 위한 Constructor. 파일 경로는 고정 경로로 지정
     * 
     * @throws IOException
     */
    public DataLoader() throws IOException {
        InputStream is = DataLoader.class.getResourceAsStream("/data.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        datas = new ArrayList<String[]>();
        makeList(bufferedReader);
        this.index = 0;
    }

    public void makeList(BufferedReader bufferedReader) throws IOException {
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineContents = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            datas.add(lineContents);
        }
    }

    // 한 행을 읽음
    public String[] nextRead() {
        if (datas.size() == index) {
            return null;
        }
        return datas.get(index++);
    }
}