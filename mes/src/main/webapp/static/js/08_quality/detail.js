window.addEventListener("load", () => {
	defGraph();
	init();
})

function init() {
	bind();
}

function bind() {
	
}

function defGraph() {
  
    new Chart(document.querySelector("#defGraph"), {
    	type : 'doughnut',
    	data : {
    		labels: ['합격', '불량'],
    		datasets : [{
    			data : [pass, defect],
    			backgroundColor : [ '#4774E9', '#E94747' ],
    			borderWidth : 0
    		}]
    	},
    	options : {
    		responsive : true,
    		cutout : '60%',
    		plugins : {
    			legend : {
    				display: false
    			},
    			tooltip : {
    				callbacks : {
    					label : function(context) {
    						const value = context.raw;
    						const percent = ((value / total) * 100).toFixed(1)
    						return `${context.label}: ${value}개 (${percent}%)`;
    					}
    				}
    			}
    			
    		}
    	}
    });
    
    setTimeout(() => {
        setLayoutHeight_snb();
    }, 50);
}