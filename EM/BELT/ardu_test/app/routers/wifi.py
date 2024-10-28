from fastapi import APIRouter, Request
import json

router = APIRouter()

@router.post("/wifi/data")
async def receive_arduino_data(request: Request):
    # 아두이노로부터 데이터 수신
    data = await request.json()
    print("Received data from Arduino:", data)
    
    # 수신한 데이터를 로그로 출력하거나 처리 가능
    return {"status": "success", "received_data": data}

@router.post("/wifi/conveyor")
async def receive_conveyor_data(request: Request):
    data = await request.json()
    print("Received conveyor data from Arduino:", data)
    conveyor_state = data.get("conveyor_state")
    return {"status": "success", "received_data": {"conveyor_state": conveyor_state}}