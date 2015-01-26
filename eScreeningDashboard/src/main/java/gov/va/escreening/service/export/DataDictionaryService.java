package gov.va.escreening.service.export;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Table;

public interface DataDictionaryService {

	public Map<String, Table<String, String, String>> createDataDictionary();

	public String getExportNameKeyPrefix();

	public String createTableResponseVarName(String exportName);

	List<Map> askFormulasFor(Object sessionObject, Integer moduleId);

	List<Map> getAllFormulas();
}
