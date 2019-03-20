<p>Write simple WEB Application what provides sending messages between users. Such
messages should be sent by email as well.</p><br>
Requirements:<br>
<ol>
<li>It should be the following pages
<ol>
<li><b>Login page</b>. It will contain:
<ol>
<li>username(email) - validation at least on the server side</li>
<li>password(at least 6 characters).</li>
</ol>
</li>
<li><b>Message list page</b>. It will include:
<ol>
<li>Place with displaying name of the current user</li>
<li>Received messages list. List include 2 columns: email(who sent
message), Message subject. Clicking for each subject the new
window/tab opens with the proper message.</li>
<li>Sent messages list. As well as previous list but with messages were
sent by you</li>
<li>“Write message” button which opens <b>“Write message page”</b></li></ol></li>
<li><b>Write message page</b>: It will have
<ol>
<li>List of recipients users(emails)</li>
<li>Message form
<ol>
<li>Recipient field. Contains recipient <b>email</b></li>
<li>Subject field and it’s not more than <u>256</u> characters</li>
<li>Message field(TextArea). Max <u>1024</u> characters</li>
<li>“Send message” button. After clicking we send message and
return to the <b>Message list page</b></li>
</ol></li></ol>
<li><b>Register user page (optional)</b>. Registration form for the new user with the
following fields:
<ol>
<li>Display name(nick) - max 128 characters</li>
<li>username(email) - max 256 characters</li>
<li>password - min 6 characters, max 128 characters</li>
<li>confirm password - as well as <u>password</u> field</li>
<li>Button register. After clicking user jumps to the <b>Login page</b> and
notification is sent to the registered user.</li></ol></li></ol></li>

<li>Use SQL database (Postgresql). It should be created 2 associated tables:
<ol>
<li>user: user_id, username(unique), password, display_name</li>
<li>message: subject, message, sender_id, recipient_id</li>
</ol>
</li>
<li>Should have SQL script what creates tables, relations between them and fill user
table</li>
<li>Build war by any build tool: <b>Ant, Maven or Gradle</b> by choice</li>
<li>Deploy application(war) to the servlet container server (Tomcat, Jetty, Grizzly or
other)</li>
</ol>

<p>Keywords: OOP, Servlet, JDBC, Regexp, Collections, JSP, JavaMail, Servlet container</p>
