function alertUser(txt) {
	if (txt.length > 0)
		alert(txt);
}

function goSubmit(s) {
	document.getElementById("submitStatus").value = s;
	// alert(document.getElementById("submitStatus").value);
}

function switchTopMenu(event) {
	var node = dojo.query(".current").attr("class", "");
	var n_id = dojo.attr(this.parentNode, "id");

	// alert( n_id);
	var n = dojo.byId(n_id);
	dojo.attr(n, "class", "current");
}

function initTopMenu() {
	var node = dojo.byId("pageNumber");
	var m = dojo.attr(node, "menu");
	// alert(m);
	var n = dojo.byId(m);
	dojo.attr(n, "class", "current");

	var o = dojo.attr(node, "sub");
	// alert(o);
	// alert(o.length);

	if (o.length > 0) {
		var p = dojo.byId(o);
		dojo.attr(p, "class", "current_sub");
	}

}

/* Start Tab */
var sub_status;
var type = 'sub';
function setType(t) {
	type = t;
}
function initSub(n, num) {
	sub_status = new Array(n);
	expandAll();
	if (num != 0)
		setSub(num);
}
function initList(n, list) {
	sub_status = new Array(n);
	expandAll();
	for (i = 0; i < list.length; i++) {
		if (list[i] != 0)
			setSub(list[i]);
	}
}
function setSub(num) {
	if (sub_status[num - 1] == 1) {
		document.getElementById('sub_' + num).style.display = 'none';
		document.getElementById('setSub_' + num).className = type + '_unactive';
		sub_status[num - 1] = 0;
	} else {
		document.getElementById('sub_' + num).style.display = '';
		document.getElementById('setSub_' + num).className = type + '_active';
		sub_status[num - 1] = 1;
	}
}
function expandAll() {
	for (i = 0; i < sub_status.length; i++) {
		sub_status[i] = 1;
	}
	for (i = 0; i < sub_status.length; i++) {
		setSub(i + 1);
	}
}
function collapseAll() {
	for (i = 0; i < sub_status.length; i++) {
		sub_status[i] = 0;
	}
	for (i = 0; i < sub_status.length; i++) {
		setSub(i + 1);
	}
}
/* End Tab */

/* Start Tab */
tab_n = 0;
tab_now = -1;
function initTab(n) {
	tab_n = n;
	showTab(1);
}
function showTab(num) {
	display_s = "";
	tab_s = "";
	if (tab_now != num) {
		for (i = 1; i <= tab_n; i++) {
			if (i == num) {
				display_s = '';
				tab_s = 'tab_active';
			} else {
				display_s = 'none';
				tab_s = 'tab';
			}
			document.getElementById('tab_' + i).style.display = display_s;
			document.getElementById('button_' + i).className = tab_s;
		}
		tab_now = num;
	}
}
/* End Tab */

/*start printing */
var win=null;
function printIt(logo,school,header,subheader,body)
{
  win = window.open();
  self.focus();
  win.document.open();
  win.document.write('<html><head><style type="text/css">a{color: #000000;text-decoration: none;}table.report {border-width: 2px;border-spacing: 2px;border-style: solid;border-collapse: collapse;}table.report td {	border-width: 2px;	padding: 2px;	border-style: solid;	border-color: black;}</style><meta http-equiv="content-type" content="text/html; charset=UTF-8"></head><body><center><p>');
  win.document.write('<img src="'+logo+'" height="90px" /><br/>');
  win.document.write('<font size="3">'+school+'</font></p><p>');
  win.document.write('<font size="6">'+header+'</font><br/>');
  win.document.write('<font size="4">'+subheader+'</font><br/></p><table width="100%" class="report">');
  win.document.write(body);
  win.document.write('</table></center></body></html>');
  win.document.close();
  win.print();
  win.close();
}
/*end printing*/