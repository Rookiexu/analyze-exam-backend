package cn.rookiex.analyze.service;

import cn.rookiex.analyze.anno.Name;
import cn.rookiex.analyze.bean.*;
import cn.rookiex.analyze.constants.MessageErrCode;
import cn.rookiex.analyze.constants.TitleConstants;
import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.ExamResult;
import cn.rookiex.analyze.entity.Student;
import cn.rookiex.analyze.message.LineResultData;
import cn.rookiex.analyze.message.Message;
import cn.rookiex.analyze.message.SeriesData;
import cn.rookiex.analyze.message.TitleInfo;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author rookiex
 * @date 2020/12/4 14:26
 * @des
 */
@Service
public class ResultService {

    private SeriesData buildLineData(Student student, Map<Integer, Exam> examIds,
                                     Map<Integer, Map<Integer, ExamResult>> examIdPidResult, boolean isRank) {
        int i;
        SeriesData lineData = new SeriesData();
        lineData.setName(student.getName());
        Number[] scores = new Number[examIds.size()];
        i = 0;
        for (Integer examId : examIds.keySet()) {
            Map<Integer, ExamResult> examResults1 = examIdPidResult.get(examId);
            ExamResult examResult = examResults1.get(student.getSId());
            if (examResult.getAbsent() == 1) {
                scores[i++] = null;
            } else {
                if (isRank) {
                    scores[i++] = examResult.getRank();
                } else {
                    scores[i++] = examResult.getScore();
                }
            }
        }
        lineData.setScore(scores);
        return lineData;
    }

    private String[] buildIndex(Map<Integer, Exam> examIds) {
        String[] index = new String[examIds.size()];
        int i = 0;
        for (Integer examId : examIds.keySet()) {
            index[i++] = examIds.get(examId).getExamName();
        }
        return index;
    }

    public LineResultData respGetClassAllExamResult(boolean isRank, List<Student> classStudents,
                                                    Map<Integer, Exam> examIds, List<ExamResult> allExamResult) {
        //成绩按照考试分类排序
        Map<Integer, Map<Integer, ExamResult>> idResultMap = sortByIdAndScore(allExamResult);

        String[] index = buildIndex(examIds);

        LineResultData lineResultData = new LineResultData();
        TitleInfo titleInfo = new TitleInfo();
        titleInfo.setTitle(TitleConstants.STUDENT_RESULT_TITLE);
        titleInfo.setYName(isRank ? TitleConstants.RANK_TITLE : TitleConstants.SCORE_TITLE);
        titleInfo.setXAxis(index);
        titleInfo.setYAxis(isRank ? getRankYAxis(allExamResult) : getScoreYAxis(allExamResult));
        titleInfo.setLegend(getLegend(allExamResult,classStudents));
        titleInfo.setInverse(isRank);
        titleInfo.setMin(isRank ? 1 : 0);
        lineResultData.setTitleInfo(titleInfo);

        for (Student student : classStudents) {
            SeriesData lineData = buildLineData(student, examIds, idResultMap, isRank);
            lineResultData.getSeriesData().add(lineData);
        }
        return lineResultData;
    }

    private String[] getLegend(List<ExamResult> allExamResult, List<Student> classStudents) {
        Map<Integer,String> nameMap = Maps.newHashMap();
        for (Student classStudent : classStudents) {
            nameMap.put(classStudent.getSId(),classStudent.getName());
        }
        Set<String> name = Sets.newHashSet();
        for (ExamResult examResult : allExamResult) {
            String s = nameMap.get(examResult.getSId());
            if (s == null) {
                continue;
            }
            name.add(s);
        }
        return name.toArray(new String[0]);
    }

    private String[] getScoreYAxis(List<ExamResult> allExamResult) {
        double minScore = 0;
        double maxScore = 1;
        for (ExamResult examResult : allExamResult) {
            double score = examResult.getScore();
            if (score > maxScore) {
                maxScore = score;
            }
            if (score < minScore) {
                minScore = score;
            }
        }
        int step = (int) ((maxScore - minScore) / 10);
        String[] strings = new String[11];

        for (int i = 0; i <= 10; i++) {
            strings[i] = String.valueOf(step * i);
        }
        return strings;
    }

    private String[] getRankYAxis(List<ExamResult> allExamResult) {
        int minRank = 1;
        int maxRank = 1;
        for (ExamResult examResult : allExamResult) {
            int rank = examResult.getRank();
            if (rank > maxRank) {
                maxRank = rank;
            }
            if (rank < minRank) {
                minRank = rank;
            }
        }

        int step = (maxRank - minRank) / 10;
        String[] strings = new String[11];

        for (int i = 0; i <= 10; i++) {
            strings[10 - i] = String.valueOf(1 + step * i);
        }

        return strings;
    }

    private void buildRankInfo(boolean isRank, List<ExamResult> allExamResult, ExamResults examResults) {
        if (isRank) {
            //最大排名
            int maxRank = getMaxRank(allExamResult);
            int height = (maxRank + 10) / 10 * 10;
            examResults.setHeight(height);
            examResults.setStepHeight(height / 10);
        }
    }


    private int getMaxRank(List<ExamResult> totalResult) {
        int maxRank = 0;
        //按照考试汇集数据
        for (List<ExamResult> value : getIdResultMap(totalResult).values()) {
            if (value.size() > maxRank) {
                maxRank = value.size();
            }
        }
        return maxRank;
    }

    private Map<Integer, List<ExamResult>> getIdResultMap(List<ExamResult> allByExamIdInAndClassId) {
        Map<Integer, List<ExamResult>> idResultMap = Maps.newTreeMap();
        for (ExamResult examResult : allByExamIdInAndClassId) {
            int examId = examResult.getExamId();
            List<ExamResult> examResults = idResultMap.get(examId);
            if (examResults == null) {
                examResults = Lists.newArrayList();
            }
            examResults.add(examResult);
            idResultMap.put(examId, examResults);
        }
        return idResultMap;
    }

    private Map<Integer, Map<Integer, ExamResult>> sortByIdAndScore(List<ExamResult> allByExamIdInAndClassId) {
        Map<Integer, Map<Integer, ExamResult>> examIdPidResultMap = Maps.newHashMap();
        //按照考试汇集数据
        Map<Integer, List<ExamResult>> idResultMap = Maps.newHashMap();
        for (ExamResult examResult : allByExamIdInAndClassId) {
            int examId = examResult.getExamId();
            int sId = examResult.getSId();

            List<ExamResult> examResults = idResultMap.get(examId);
            if (examResults == null) {
                examResults = Lists.newArrayList();
            }
            examResults.add(examResult);
            idResultMap.put(examId, examResults);

            Map<Integer, ExamResult> pResult = examIdPidResultMap.get(examId);
            if (pResult == null) {
                pResult = Maps.newHashMap();
            }
            pResult.put(sId, examResult);
            examIdPidResultMap.put(examId, pResult);
        }

        //按照成绩排序
        for (List<ExamResult> value : idResultMap.values()) {
            value.sort(ExamResult::compareTo);
            int i = 1;
            for (ExamResult examResult : value) {
                examResult.setRank(i++);
            }
        }

        return examIdPidResultMap;
    }

    public LineResultData respExamResultsFail(String errMsg) {
        return null;
    }

    public ExamResults respGetClassAllExamHistory(Map<Integer, Exam> examMaps, List<ExamResult> allByExamIdInAndClassId) {
        return respGetClassAllExamHistory(examMaps, allByExamIdInAndClassId, null);
    }

    private List<lineData> buildLineDates(Map<Integer, Exam> examMaps, Map<Integer, ClassExamResult> classResultMap, Set<String> prams) {
        List<lineData> lineDataList = Lists.newArrayList();
        Field[] fields = ClassExamResult.class.getFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Name) {
                    String name = ((Name) annotation).name();
                    if (prams != null) {
                        if (!prams.contains(name)) {
                            continue;
                        }
                    }

                    lineData lineData = new lineData();
                    lineData.setName(name);
                    Object[] scores = new Object[examMaps.size()];
                    int i = 0;
                    for (Integer examId : examMaps.keySet()) {
                        ClassExamResult classExamResult = classResultMap.get(examId);
                        try {
                            Object o = field.get(classExamResult);
                            scores[i++] = o;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    lineData.setScores(scores);
                    lineDataList.add(lineData);
                }
            }
        }
        return lineDataList;
    }


    private ClassExamResult buildClassResult(Integer examId, List<ExamResult> examResults) {
        int perfectCount = 0;
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        int dCount = 0;
        int totalScore = 0;
        int testCount = 0;
        int absentCount = 0;
        for (ExamResult examResult : examResults) {
            boolean isAbsent = examResult.getAbsent() == 1;
            if (isAbsent) {
                absentCount++;
            } else {
                testCount++;
            }
            double score = examResult.getScore();
            totalScore += score;
            if (score == 100) {
                perfectCount++;
            }

            if (score >= 90) {
                aCount++;
            } else if (score >= 80) {
                bCount++;
            } else if (score >= 60) {
                cCount++;
            } else {
                dCount++;
            }
        }

        ClassExamResult.ClassExamResultBuilder builder = ClassExamResult.builder();
        builder.examId(examId).aCount(aCount).bCount(bCount).cCount(cCount).dCount(dCount)
                .testCount(testCount).absentCount(absentCount).perfectCount(perfectCount);

        double i = aCount * 10000 / testCount;
        double i1 = bCount * 10000 / testCount;
        double i2 = cCount * 10000 / testCount;
        double i3 = dCount * 10000 / testCount;
        double i4 = totalScore * 100 / testCount;
        builder.avg(i4 / 100)
                .aRate(i / 100)
                .bRate(i1 / 100)
                .cRate(i2 / 100)
                .dRate(i3 / 100);

        return builder.build();
    }

    public Map<Integer, ClassExamResult> getClassResultData(List<ExamResult> allByExamIdInAndClassId){
        Map<Integer, ClassExamResult> classResultMap = Maps.newTreeMap();
        Map<Integer, List<ExamResult>> idResultMap = getIdResultMap(allByExamIdInAndClassId);
        for (Integer examId : idResultMap.keySet()) {
            ClassExamResult classResult = buildClassResult(examId, idResultMap.get(examId));
            classResultMap.put(examId, classResult);
        }
        return classResultMap;
    }

    public ExamResults respGetClassAllExamHistory(Map<Integer, Exam> examMaps, List<ExamResult> allByExamIdInAndClassId, Set<String> prams) {
        Map<Integer, List<ExamResult>> idResultMap = getIdResultMap(allByExamIdInAndClassId);
        Map<Integer, ClassExamResult> classResultMap = Maps.newTreeMap();
        for (Integer examId : idResultMap.keySet()) {
            ClassExamResult classResult = buildClassResult(examId, idResultMap.get(examId));
            classResultMap.put(examId, classResult);
        }

        ExamResults examResults = new ExamResults();
        String[] index = buildIndex(examMaps);
        examResults.setIndex(index);
        examResults.setFromBig(1);

        List<lineData> lineDates = buildLineDates(examMaps, classResultMap, prams);
        examResults.getDatasets().addAll(lineDates);
        return examResults;
    }

    public String getResult(){
        Message message = new Message();
        return JSONObject.toJSONString(message);
    }

    public String getResult(Object object){
        Message message = new Message();
        message.setData(object);
        return JSONObject.toJSONString(message);
    }

    public String getErrResult(MessageErrCode messageErrCode,String errMsg){
        Message message = new Message();
        message.setMessage(errMsg);
        message.setCode(messageErrCode.getErrCode());
        return JSONObject.toJSONString(message);
    }

}
