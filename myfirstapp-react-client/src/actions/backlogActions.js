import axios from "axios";

export const addProjectTask = (backlog_id, project_task, history) => async (
  dispatch
) => {
  const res = await axios.post(`/api/backlog/${backlog_id}`, project_task);
  history.push(`/projectBoard/${backlog_id}`);
};
