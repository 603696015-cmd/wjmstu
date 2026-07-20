package com.sopia.assistman.entities;

import com.sopia.questionman.entities.StuffLib;

public class PlanStuff {
	private int id;
	private Plan plan;
	private StuffLib stuff;
	private PlanStage planStage;

	public PlanStuff() {
	}

	public PlanStuff(int id) {
		this.id = id;
	}

	public PlanStage getPlanStage() {
		return planStage;
	}

	public void setPlanStage(PlanStage planStage) {
		this.planStage = planStage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StuffLib getStuff() {
		return stuff;
	}

	public void setStuff(StuffLib stuff) {
		this.stuff = stuff;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

}
