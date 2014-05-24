package com.zuzu.queue;

public abstract interface QueueCommand {
	public abstract void execute();

	public abstract void cancel();
}
