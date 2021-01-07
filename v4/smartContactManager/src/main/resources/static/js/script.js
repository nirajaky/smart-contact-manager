console.log("this is script file...");

const toggleSidebar = () => {

    //using jquery to show/hide sidebar
    if ($(".sidebar").is(":visible")) {
        //true
        //hiding sidebar now

        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%"); //when sidebar place is empty, we are have to make margin 0, so content area shift to left side

    } else{
        //false
        //showing sidebar

        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
    }
};