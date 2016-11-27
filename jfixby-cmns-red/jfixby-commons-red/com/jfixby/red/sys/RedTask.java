
package com.jfixby.red.sys;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.taskman.Job;
import com.jfixby.cmns.api.taskman.TASK_STATE;
import com.jfixby.cmns.api.taskman.Task;
import com.jfixby.cmns.api.taskman.TaskSpecs;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.api.util.StateSwitcher;

public class RedTask implements Task, Runnable {

	private final List<Job> jobs = Collections.newList();

	int job_to_do = -1;

	private final StateSwitcher<TASK_STATE> switcher;
	private String name;

	final private boolean runInSeparatedThread;

	@Override
	public String toString () {
		return "Task[" + this.name + "]";
	}

	public RedTask (final String name, final Job job) {
		this(name, Collections.newList(job));
	}

	public RedTask (final String name, final Collection<Job> jobs) {
		this.name = name;
		if (name == null) {
			this.name = super.toString();
		}
		this.jobs.addAll(jobs);
		this.switcher = JUtils.newStateSwitcher(TASK_STATE.ACTIVE);
		this.runInSeparatedThread = false;
		this.t = null;
		// listNames();
	}

	public RedTask (final TaskSpecs specs) {
		this.name = specs.getName();
		if (this.name == null) {
			this.name = super.toString();
		}
		this.runInSeparatedThread = specs.runInSeparatedThread();

		this.jobs.addAll(specs.listJobs());

		this.switcher = JUtils.newStateSwitcher(TASK_STATE.ACTIVE);

		if (this.runInSeparatedThread) {
			this.t = new Thread(this.runner);
			this.startThread();
		} else {
			this.t = null;
		}
	}

	private final Runnable runner = this;
	final Thread t;

	private void startThread () {

	}

	boolean first_call = false;
	Job current_job;

	public void push () {
		if (!this.runInSeparatedThread) {
			this.pushTask();
		} else {
			Sys.yeld();
		}
	}

	@Override
	public void run () {
		this.pushTask();
	}

	private void pushTask () {
		this.switcher.expectState(TASK_STATE.ACTIVE);
		if (this.job_to_do == -1) {
			this.job_to_do++;
			this.first_call = true;
		}

		this.current_job = this.jobs.getElementAt(this.job_to_do);
		if (this.first_call) {
			try {
				this.current_job.doStart();
				this.first_call = false;
			} catch (final Throwable e) {
				e.printStackTrace();
				this.switcher.switchState(TASK_STATE.FAILED);
				return;
			}
		}

		try {
			this.current_job.doPush();
		} catch (final Throwable e) {
			Err.reportError(e);
			this.switcher.switchState(TASK_STATE.FAILED);
			return;
		}

		if (this.current_job.isDone()) {
			this.job_to_do++;
			this.first_call = true;
		}
		if (this.job_to_do >= this.jobs.size()) {
			// L.d("task done", this);
			this.switcher.switchState(TASK_STATE.SUCCESSFULLY_COMPLETED);
			return;
		}

	}

	@Override
	public boolean isActive () {
		return this.switcher.currentState() == TASK_STATE.ACTIVE;
	}

	@Override
	public boolean isFailed () {
		return this.switcher.currentState() == TASK_STATE.FAILED;
	}

	@Override
	public boolean isSuccessfullyCompleted () {
		return this.switcher.currentState() == TASK_STATE.SUCCESSFULLY_COMPLETED;
	}

	@Override
	public TASK_STATE getState () {
		return this.switcher.currentState();
	}

}
