<!DOCTYPE html>
<head>
    <title>Registration</title>

    <style type="text/css">
        #seekerform{
            display: none
        }
        #sitterform{
            display: none
        }
        #radiocheck{
            display: none
        }
        #submit{
            display: none
        }

    </style>
</head>
<body>

        Seeker: <input type="radio" value="seeker" name="membertype" id="radioseeker_selected"><br>
        Sitter: <input type="radio" value="sitter" name="membertype" id="radiositter_selected"><br>

        <button type="button" onclick="viewform()">Fill Form</button>

        <form id="seekerform">
            <h3>Seeker Form</h3>
            <p>Seeker:</p>
            <input type="radio" value="seeker" name="membertype" id="radioseeker"><br>
            <p>First name:</p>
            <input type="text" name="firstname"><br>
            <p>Last name:</p>
            <input type="text" name="lastname"><br>
            <p>Phone number:</p>
            <input type="text" name="phone"><br>
            <p>Email:</p>
            <input type="text" name="email"><br>
            <p>Address:</p>
            <input type="text" name="address"><br>
            <p>Spouse name:</p>
            <input type="text" name="spousename"><br>
            <p>No of Children:</p>
            <input type="text" name="children"><br>
            <br>
            <input type="submit" value="Submit">
        </form>

        <form id="sitterform">
            <h3>Sitter Form</h3>
            <p>Sitter:</p>
            <input type="radio" value="sitter" name="membertype" id="radiositter"><br>
            <p>First name:</p>
            <input type="text" name="firstname"><br>
            <p>Last name:</p>
            <input type="text" name="lastname"><br>
            <p>Phone number:</p>
            <input type="text" name="phone"><br>
            <p>Email:</p>
            <input type="text" name="email"><br>
            <p>Address:</p>
            <input type="text" name="address"><br>
            <p>Experience:</p>
            <input type="text" name="experience"><br>
            <p>Expected Pay:</p>
            <input type="text" name="expectedpay"><br>
            <br>
            <input type="submit" value="Submit">
        </form>

    <script type="text/javascript">
        function viewform(){
            var seeker_selected = document.getElementById("radioseeker_selected").checked;
            var sitter_selected = document.getElementById("radiositter_selected").checked;
            var seekerform = document.getElementById("seekerform");
            var sitterform = document.getElementById("sitterform");
            if(seeker_selected){
                document.getElementById("radioseeker").checked = seeker_selected;
                seekerform.style.display = "inline";
                sitterform.style.display = "none";
            }
            if(sitter_selected){
                document.getElementById("radiositter").checked = sitter_selected;
                sitterform.style.display = "inline";
                seekerform.style.display = "none";
            }
        }
    </script>

</body>
</html>