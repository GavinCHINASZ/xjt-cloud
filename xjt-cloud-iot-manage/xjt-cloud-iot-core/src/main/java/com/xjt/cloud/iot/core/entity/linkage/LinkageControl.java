 package com.xjt.cloud.iot.core.entity.linkage;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

 /**
  * @Description: 联动控制 实体类
  * @Author huanggc
  * @Date 2019/09/19
  **/
public class LinkageControl extends BaseEntity {
	// 规则名称
	private String ruleName;
	// 规则描述
	private String ruleDescription;
	// 状态 1:启用  2:禁用
	private Integer state;
	// 关系 1:AND  2:OR
	private Integer relation;

	private List<Long> ids;

	 public String getRuleName() {
		 return ruleName;
	 }

	 public void setRuleName(String ruleName) {
		 this.ruleName = ruleName;
	 }

	 public String getRuleDescription() {
		 return ruleDescription;
	 }

	 public void setRuleDescription(String ruleDescription) {
		 this.ruleDescription = ruleDescription;
	 }

	 public Integer getState() {
		 return state;
	 }

	 public void setState(Integer state) {
		 this.state = state;
	 }

	 public Integer getRelation() {
		 return relation;
	 }

	 public void setRelation(Integer relation) {
		 this.relation = relation;
	 }

	 public List<Long> getIds() {
		 return ids;
	 }

	 public void setIds(List<Long> ids) {
		 this.ids = ids;
	 }
 }