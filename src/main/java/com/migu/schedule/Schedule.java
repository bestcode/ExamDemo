package com.migu.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.MyTaskInfo;
import com.migu.schedule.info.TaskInfo;

/*
*类名和方法不能修改
 */
public class Schedule {

	// 挂起队列
	private List<MyTaskInfo> suspend;
	// 节点池
	private Map<Integer, List<MyTaskInfo>> nodePool;
	// 任务编号唯一性检查
	private Set<MyTaskInfo> taskIds;

    public int init() {
		suspend = new ArrayList<MyTaskInfo>();
		nodePool = new HashMap<Integer, List<MyTaskInfo>>();
		taskIds = new HashSet<MyTaskInfo>();

        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
		if (nodeId <= 0)
			return ReturnCodeKeys.E004;

		if (nodePool.get(nodeId) != null)
			return ReturnCodeKeys.E005;

		nodePool.put(nodeId, new ArrayList<MyTaskInfo>());

		return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
		if (nodeId <= 0)
			return ReturnCodeKeys.E004;

		List<MyTaskInfo> tmp = nodePool.remove(nodeId);

		if (tmp == null)
			return ReturnCodeKeys.E007;
		else
			suspend.addAll(tmp);

		return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
		if (taskId <= 0)
			return ReturnCodeKeys.E009;

		MyTaskInfo ta = new MyTaskInfo(new TaskInfo(), taskId, -1, consumption);
		if (!taskIds.add(ta))
			return ReturnCodeKeys.E010;

		suspend.add(ta);
		return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
		if (taskId <= 0)
			return ReturnCodeKeys.E009;

		MyTaskInfo ta = new MyTaskInfo(new TaskInfo(), taskId, -1);
		if (!taskIds.contains(ta))
			return ReturnCodeKeys.E012;

		int nodeid = 0;
		for (MyTaskInfo my : taskIds) {
			if (my.getObj().getTaskId() == taskId) {
				nodeid = my.getObj().getNodeId();
				break;
			}
		}

		if (nodeid <= 0) {
			suspend.remove(ta);
		} else {
			List<MyTaskInfo> list = nodePool.get(nodeid);
			list.remove(ta);
		}

		return ReturnCodeKeys.E011;
    }

    public int scheduleTask(int threshold) {
		if (threshold <= 0)
			return ReturnCodeKeys.E002;

		List<MyTaskInfo> consumptionList = new ArrayList<MyTaskInfo>();
		consumptionList.addAll(taskIds);
		consumptionList.sort((MyTaskInfo t1, MyTaskInfo t2) -> Integer.compare(t2.getConsumption(), t1.getConsumption()));

		List<Integer> nodeSortList = new ArrayList<Integer>();
		nodeSortList.addAll(nodePool.keySet());
		nodeSortList.sort((Integer t1, Integer t2) -> Integer.compare(t1, t2));

		return ReturnCodeKeys.E013;
    }

    public int queryTaskStatus(List<TaskInfo> tasks) {
		if (tasks == null)
			return ReturnCodeKeys.E016;

		tasks.clear();

		// 填充
		for (MyTaskInfo my : suspend)
			tasks.add(my.getObj());

		for (List<MyTaskInfo> list : nodePool.values()) {
			for (MyTaskInfo my : list)
				tasks.add(my.getObj());
		}

		// 排序
		tasks.sort((TaskInfo t1, TaskInfo t2) -> Integer.compare(t1.getTaskId(), t2.getTaskId()));

		return ReturnCodeKeys.E015;
    }

}
