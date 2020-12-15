package cn.rookiex.analyze.message;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author rookiex
 * @date 2020/12/15 17:44
 * @des
 */
@Data
public class ClassesData {
    private List<Map<String,Object>> class_options;
    private List<Map<String,Object>> grade_options;
}
