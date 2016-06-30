package mybatis;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class DatabaseNamesPlugin extends PluginAdapter {
	private int commentRows=5;
	public boolean validate(List<String> arg0) { 
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		super.initialized(introspectedTable);
	}
	@Override
	public boolean sqlMapCountByExampleElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
	}




	@Override
	public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapDeleteByExampleElementGenerated(element, introspectedTable);
	}




	@Override
	public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapDeleteByPrimaryKeyElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		modifyTableName(element,1,introspectedTable);
		return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
	}




	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,3,introspectedTable);
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,3,introspectedTable);
		return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		modifyTableName(element,2,introspectedTable);
		return super.sqlMapSelectByPrimaryKeyElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapUpdateByExampleSelectiveElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapUpdateByExampleSelectiveElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(element,
				introspectedTable);
	}




	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		modifyTableName(element,0,introspectedTable);
		return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element,
				introspectedTable);
	}


	@Override
	public boolean sqlMapInsertElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		modifyTableName(element,1,introspectedTable);
		return super.sqlMapInsertElementGenerated(element, introspectedTable);
	}
	
	private void modifyTableName(XmlElement element,int index,IntrospectedTable introspectedTable){
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		String rowContent =  element.getElements().get(commentRows+index).getFormattedContent(0);
		String schema = introspectedTable.getTableConfiguration().getSchema();
		element.getElements().remove(commentRows+index);
		element.addElement(commentRows+index, new TextElement(rowContent.replace(tableName,schema+"."+tableName).toString()));
	//	element.addElement(0,new TextElement(introspectedTable.getSelectByExampleQueryId()));
	}

}
