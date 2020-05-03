import React, { Component } from "react";
import { connect } from "react-redux";
import { getProject, createProject } from "../../actions/projectActions";
import PropTypes from "prop-types";
import classnames from "classnames";

class UpdateProject extends Component {
  constructor() {
    super();
    this.state = {
      id: "",
      project_Name: "",
      projectDescription: "",
      projectIdentifier: "",
      start_date: "",
      end_date: "",
      errors: {},
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    const {
      id,
      project_Name,
      projectDescription,
      projectIdentifier,
      start_date,
      end_date,
    } = nextProps.project;

    this.setState({
      id,
      project_Name,
      projectDescription,
      projectIdentifier,
      start_date,
      end_date,
    });

    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getProject(id, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const updateProject = {
      id: this.state.id,
      project_Name: this.state.project_Name,
      projectDescription: this.state.projectDescription,
      projectIdentifier: this.state.projectIdentifier,
      start_date: this.state.start_date,
      end_date: this.state.end_date,
    };

    this.props.createProject(updateProject, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Update Project form</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.project_Name,
                    })}
                    placeholder="Project Name"
                    name="project_Name"
                    value={this.state.project_Name}
                    onChange={this.onChange}
                  />

                  {errors.project_Name && (
                    <div className="invalid-feedback">
                      {errors.project_Name}
                    </div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Unique Project ID"
                    name="projectIdentifier"
                    value={this.state.projectIdentifier}
                    onChange={this.onChange}
                    disabled
                  />
                </div>

                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Project Description"
                    name="projectDescription"
                    value={this.state.projectDescription}
                    onChange={this.onChange}
                  ></textarea>
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="start_date"
                    value={this.state.start_date}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Estimated End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="end_date"
                    value={this.state.end_date}
                    onChange={this.onChange}
                  />
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateProject.propTypes = {
  project: PropTypes.object.isRequired,
  getProject: PropTypes.func.isRequired,
  createProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project.project,
  errors: state.errors,
});

export default connect(mapStateToProps, { getProject, createProject })(
  UpdateProject
);
