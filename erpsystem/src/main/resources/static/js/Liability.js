document.addEventListener('DOMContentLoaded', function () {
    const liabilityTableBody = document.querySelector('#liabilityTable tbody');
    const liabilitySelect = document.getElementById('repaymentName');
    let totalLiabilityAmount = 0;

    // 부채 데이터 가져오기
    fetch('/api/rest/liability')
        .then(response => response.json())
        .then(data => {
            data.forEach(liability => {
                // 테이블에 행 추가
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

                // 드롭다운 옵션 추가
                const option = document.createElement('option');
                option.value = liability.name;
                option.textContent = liability.name;
                liabilitySelect.appendChild(option);

                // 총액 계산
                totalLiabilityAmount += parseFloat(liability.currentValue);
            });

            // 총액 표시
            document.getElementById('totalLiabilityAmount').innerText = totalLiabilityAmount.toLocaleString() + '원';
        })
        .catch(error => {
            console.error('부채 데이터 가져오기 오류:', error);
        });
});

// 부채 상환 폼 보여주기
function showRepaymentForm() {
    document.getElementById('repaymentForm').style.display = 'block';
}

// 부채 상환 폼 숨기기
function hideRepaymentForm() {
    document.getElementById('repaymentForm').style.display = 'none';
}

// 부채 상환 요청
function submitRepayment() {
    const name = document.getElementById('repaymentName').value;
    const amount = document.getElementById('repaymentAmount').value;

    if (!name || !amount) {
        alert('부채 이름과 상환 금액을 모두 입력하세요.');
        return;
    }

    fetch('/repayDebt', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: name,
            amount: amount,
        }),
    })
        .then(response => {
            if (response.ok) {
                alert('부채 상환이 완료되었습니다.');
                hideRepaymentForm();
                location.reload(); // 페이지 새로고침
            } else {
                alert('부채 상환에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('부채 상환 요청 오류:', error);
            alert('부채 상환 중 오류가 발생했습니다.');
        });
}
