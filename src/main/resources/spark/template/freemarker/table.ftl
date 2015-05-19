<html>

<head>
<link rel="stylesheet" type="text/css"
	href="../css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="../js/jquery.js"></script>
<script type="text/javascript" charset="utf8"
	src="../js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#table_id_${tableid}').DataTable(${dataTableSetup});
	});
</script>
</head>

<body>
	<table id="table_id_${tableid}" class="display" width="100%"
		cellspacing="0">
		<thead>
			<tr>
				<#list columnNames as col>
				  <th>${col}</th>
				</#list>
			</tr>
		</thead>
	</table>
</body>
</html>