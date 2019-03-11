<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:navigation-bar title="Login">
	<div class="container">
		<div class="d-flex align-items-center" style="height: 100vh">
			<div class="col-4 mx-auto">
				<div class="card">
					<div class="card-header">
						<div class="d-flex justify-content-center">                        
                            <h3 class="card-title mb-1 mt-1">Sign Up</h3>
                        </div>
					</div>
					<div class="card-body">
						<c:url var="urlSaveUser" value="/authorization/registration.html" />
						<form action="${urlSaveUser}" method="post">
							<div class="form-group">
								<input name="displayName" type="text" class="form-control" maxlength="128" placeholder="Enter nickname">
							</div>
							<div class="form-group">
								<input name="username" type="email" class="form-control" maxlength="128" placeholder="Enter email">
							</div>
							<div class="form-group">
								<input name="password" pattern=".{0}|{6,128}" required title="password should've at least 6 characters" type="password" class="form-control" placeholder="Enter password">
							</div>
							<div class="form-group">
								<input name="confirm" pattern=".{0}|{6,128}" required title="password should've at least 6 characters" type="password" class="form-control" placeholder="Confirm password">
							</div>
							<button type="submit" class="btn btn-block btn-primary">Create account</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</u:navigation-bar>