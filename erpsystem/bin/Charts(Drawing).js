window.onload = loadCharts;

async function loadCharts() {
	try {
		const response = await fetch('http://localhost:8080/api/charts?startDate=2023-01-01T00:00&endDate=2023-12-31T23:59');
		const data = await response.json();

		const financialRatios = data.financialRatios;
		const salesAndNetProfit = data.salesAndNetProfit;
		const costSummary = data.costSummary;
		const capitalLiabilityRatio = data.capitalLiabilityRatio;
		const liabilities = data.liabilities;
		const additionalData = data.additionalData; // 추가 차트 데이터가 있다면

		// 각 차트 생성 함수 호출
		createFinancialRatioChart(financialRatios);
		createSalesNetProfitChart(salesAndNetProfit);
		createOperatingCostChart(costSummary);
		createCapitalLiabilityChart(capitalLiabilityRatio);
		createLiabilityChart(liabilities);
		createAdditionalChart(additionalData);
	} catch (error) {
		console.error('데이터 가져오기 오류:', error);
	}
}

// 1. 재무 비율 분석 차트 생성 함수
function createFinancialRatioChart(financialRatios) {
	const labels = ['기간 1', '기간 2', '기간 3', '기간 4'];
	const roeData = financialRatios.ROEs.map(value => parseFloat(value.toFixed(2)));
	const roaData = financialRatios.ROAs.map(value => parseFloat(value.toFixed(2)));

	const data = {
		labels: labels,
		datasets: [
			{
				label: 'ROE (%)',
				data: roeData,
				borderColor: 'rgba(75, 192, 192, 1)',
				backgroundColor: 'rgba(75, 192, 192, 0.2)',
				fill: false,
				tension: 0.1
			},
			{
				label: 'ROA (%)',
				data: roaData,
				borderColor: 'rgba(255, 99, 132, 1)',
				backgroundColor: 'rgba(255, 99, 132, 0.2)',
				fill: false,
				tension: 0.1
			}
		]
	};

	const config = {
		type: 'line',
		data: data,
		options: {
			scales: {
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: '비율 (%)'
					}
				}
			}
		}
	};

	const ctx = document.getElementById('financialRatioChart').getContext('2d');
	new Chart(ctx, config);
}

// 2. 매출액 및 순이익 차트 생성 함수
function createSalesNetProfitChart(salesAndNetProfit) {
	const labels = ['1분기', '2분기', '3분기', '4분기'];
	const salesData = salesAndNetProfit.Sales.map(value => parseFloat(value) / 10000); // 만 원 단위로 나누기
	const netProfitData = salesAndNetProfit.NetProfit.map(value => parseFloat(value) / 10000); // 만 원 단위로 나누기

	const data = {
		labels: labels,
		datasets: [
			{
				type: 'bar',
				label: '매출액 (만 원)',
				data: salesData,
				backgroundColor: 'rgba(54, 162, 235, 0.5)',
				yAxisID: 'y'
			},
			{
				type: 'line',
				label: '순이익 (만 원)',
				data: netProfitData,
				borderColor: 'rgba(255, 99, 132, 1)',
				backgroundColor: 'rgba(255, 99, 132, 0.2)',
				fill: false,
				yAxisID: 'y1'
			}
		]
	};

	const config = {
		data: data,
		options: {
			scales: {
				y: {
					type: 'linear',
					position: 'left',
					beginAtZero: true,
					title: {
						display: true,
						text: '매출액 (만 원)'
					},
					ticks: {
						callback: function(value) {
							return value;
						}
					}
				},
				y1: {
					type: 'linear',
					position: 'right',
					beginAtZero: true,
					grid: {
						drawOnChartArea: false
					},
					title: {
						display: true,
						text: '순이익 (만 원)'
					},
					ticks: {
						callback: function(value) {
							return value;  // 만 원 단위로 표시
						}
					}
				}
			}
		}
	};

	const ctx = document.getElementById('salesNetProfitChart').getContext('2d');
	new Chart(ctx, config);
}


// 3. 운영비용 분포 차트 생성 함수
function createOperatingCostChart(costSummary) {
	const sortedCosts = Object.entries(costSummary)
		.map(([key, value]) => ({ label: key, value: Math.abs(value) / 10000 })) // 만 원 단위로 나누기
		.sort((a, b) => b.value - a.value);

	const labels = sortedCosts.map(item => item.label);
	const dataValues = sortedCosts.map(item => item.value);

	const data = {
		labels: labels,
		datasets: [
			{
				label: '운영비용 (만 원)',
				data: dataValues,
				backgroundColor: 'rgba(153, 102, 255, 0.5)'
			}
		]
	};

	const config = {
		type: 'bar',
		data: data,
		options: {
			indexAxis: 'y',
			scales: {
				x: {
					beginAtZero: true,
					title: {
						display: true,
						text: '금액 (만 원)'
					},
					ticks: {
						callback: function(value) {
							return value;  // 만 원 단위로 표시
						}
					}
				}
			}
		}
	};

	const ctx = document.getElementById('operatingCostChart').getContext('2d');
	new Chart(ctx, config);
}


// 4. 자본 및 부채 비율 차트 생성 함수
function createCapitalLiabilityChart(capitalLiabilityRatio) {
	const labels = ['자본', '부채'];
	const dataValues = [
		parseFloat(capitalLiabilityRatio.Capital) / 10000, // 만 원 단위로 나누기
		parseFloat(capitalLiabilityRatio.Libility) / 10000 // 만 원 단위로 나누기
	];

	const data = {
		labels: labels,
		datasets: [
			{
				data: dataValues,
				backgroundColor: [
					'rgba(255, 205, 86, 0.5)',
					'rgba(75, 192, 192, 0.5)'
				]
			}
		]
	};

	const config = {
		type: 'pie',
		data: data,
		options: {
			plugins: {
				tooltip: {
					callbacks: {
						label: function(context) {
							const label = context.label || '';
							const value = context.formattedValue;
							const total = context.dataset.data.reduce((a, b) => a + b, 0);
							const percentage = ((context.raw / total) * 100).toFixed(2) + '%';
							return `${label}: ${value} (${percentage})`; // 만 원 단위로 표시
						}
					}
				}
			}
		}
	};

	const ctx = document.getElementById('capitalLiabilityChart').getContext('2d');
	new Chart(ctx, config);
}


// 5. 부채 차트 생성 함수
function createLiabilityChart(liabilities) {
	const labels = ['기간 1', '기간 2', '기간 3', '기간 4'];
	const liabilitiesData = liabilities.map(value => parseFloat(value.toFixed(2)));

	// 디버깅용 콘솔 로그
	console.log('부채 데이터:', liabilitiesData);

	const data = {
		labels: labels,
		datasets: [
			{
				label: '부채 (원)',
				data: liabilitiesData,
				borderColor: 'rgba(75, 192, 192, 1)',
				backgroundColor: 'rgba(75, 192, 192, 0.2)',
				fill: false,
				tension: 0.1
			},
		]
	};

	const config = {
		type: 'line',
		data: data,
		options: {
			scales: {
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: '원'
					}
				}
			}
		}
	};

	const ctx = document.getElementById('liabilityChart').getContext('2d');
	new Chart(ctx, config);
}

// 6. 추가 예정 차트 생성 함수
function createAdditionalChart(additionalData) {
	const labels = ['항목 1', '항목 2', '항목 3', '항목 4'];
	const additionalChartData = additionalData.map(value => parseFloat(value.toFixed(2)));

	const data = {
		labels: labels,
		datasets: [
			{
				label: '추가 차트 데이터',
				data: additionalChartData,
				borderColor: 'rgba(255, 159, 64, 1)',
				backgroundColor: 'rgba(255, 159, 64, 0.2)',
				fill: false,
				tension: 0.1
			},
		]
	};

	const config = {
		type: 'line',
		data: data,
		options: {
			scales: {
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: '값'
					}
				}
			}
		}
	};

	const ctx = document.getElementById('additionalChart').getContext('2d');
	new Chart(ctx, config);
}