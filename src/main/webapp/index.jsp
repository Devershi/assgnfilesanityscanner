<html>
<body>
<h2>Document Sanity Check!</h2>
<html>
<body>
<h1>File Upload with Jersey</h1>

<form action="webapi/file/listOfRestrictedWords" method="post" enctype="multipart/form-data">
    <p>
        Select a file to find profane words : <input type="file" name="file" size="45"/>
    </p>
    <input type="submit" value="Find Profane Words"/>
</form>

<form action="webapi/file/checkForProfanity" method="post" enctype="multipart/form-data">
    <p>
        Select a file to check for profanity : <input type="file" name="file" size="45"/>
    </p>
    <input type="submit" value="Check File For Profanity"/>
</form>
</body>
</html>
</body>
</html>
