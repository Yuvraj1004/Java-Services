package com.incture.cherrywork.workflow.services;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.WorkflowResponseEntity;

public interface ApprovalworkflowTriggerImpl {

	public WorkflowResponseEntity approvalWorkflowTriggeringAndUpdateTable(String salesOrderNo, String requestId,
			String approver, String level, String dataSet);

	public WorkflowResponseEntity approvalWorkflowTriggerOnApproverTye(String salesOrderNo, String requestId,
			String approver, String level, String dataSet, String ApproverType);

	public ResponseEntity closeAllWorkflowsByBussinessKey(String bussinessKey);
}
