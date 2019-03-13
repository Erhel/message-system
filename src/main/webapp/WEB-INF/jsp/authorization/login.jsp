<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:navigation-bar title="Login">
	<div class="container">
		<div class="d-flex align-items-center" style="height: 100vh">
			<div class="col-4 mx-auto">
				<div class="card">
					<div class="card-header">
                        <div class="text-center">                        
    						<h3 class="card-title mb-1 mt-1">Sign In</h3>
                        </div>
					</div>
					<div class="card-body">
						<c:url var="urlLogin" value="/authorization/login.html"/>
						<form action="${urlLogin}" method="post">
							<div class="form-group">
								<input name="login" type="email" class="form-control" placeholder="Enter email">
							</div>
							<div class="form-group">
								<input name="password" type="password" required class="form-control" placeholder="Enter password">
							</div>
							<button type="submit" class="btn btn-block btn-primary">Login</button>
						</form>
					</div>
                    <div class="card-footer">
                        <c:url var="urlEditRegistration" value="/authorization/registration.html"/>
                        <div class="d-flex justify-content-center links">                        
                            Don't have an account?<a href="${urlEditRegistration}">Sign Up</a>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
</u:navigation-bar>