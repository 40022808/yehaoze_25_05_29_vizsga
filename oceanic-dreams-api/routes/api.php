<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\BerlesController;



Route::get('/berlesek', [BerlesController::class, 'index']);          
Route::get('/berlesek/{id}', [BerlesController::class, 'show']);      
Route::post('/berlesek', [BerlesController::class, 'store']);        
Route::put('/berlesek/{id}', [BerlesController::class, 'update']);    
Route::delete('/berlesek/{id}', [BerlesController::class, 'destroy']); 