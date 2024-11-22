document.addEventListener('DOMContentLoaded', function () {
    // 부채 데이터 가져오기
    fetch('/api/liability')
        .then(response => response.json())
        .then(data => {
            const liabilities = data.liability;
            const liabilityTableBody = document.querySelector('#liabilityTable tbody');
            let totalLiabilityAmount = 0;

            liabilities.forEach(liability => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${liability.id}</td>
                    <td>${liability.name}</td>
                    <td>${parseFloat(liability.currentValue).toLocaleString()}원</td>
                    <td>${parseFloat(liability.originValue).toLocaleString()}원</td>
                    <td>${liability.type}</td>
                    <td>${liability.interestRate}%</td>
                    <td>${liability.interestPeriod}</td>
                    <td>${liability.maturityDate}</td>
                `;
                liabilityTableBody.appendChild(row);
                totalLiabilityAmount += parseFloat(liability.currentValue);
            });

            document.getElementById('totalLiabilityAmount').innerText = totalLiabilityAmount.toLocaleString() + '원';
        })
        .catch(error => {
            console.error('부채 데이터 가져오기 오류:', error);
        });
});