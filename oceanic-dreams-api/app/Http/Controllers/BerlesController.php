<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Berlesek;


class BerlesController extends Controller
{
    
    public function index()
    {
        return response()->json(Berlesek::all(), 200);
    }

    
    public function show($id)
    {
        $Berlesek = Berlesek::findOrFail($id);
        return response()->json($Berlesek, 200);
    }

    
    public function store(Request $request)
    {
        $validated = $request->validate([
            'uid' => 'required|integer',
            'yachtId' => 'required|integer',
            'startDate' => 'required|date|after:tomorrow',
            'endDate' => 'required|date|after_or_equal:startDate',
            'dailyPrice' => 'required|integer|min:1',
            'deposit' => 'required|integer|min:0'
        ]);

        
        $overlap = Berlesek::where('yachtId', $validated['yachtId'])
            ->where(function ($query) use ($validated) {
                $query->whereBetween('startDate', [$validated['startDate'], $validated['endDate']])
                    ->orWhereBetween('endDate', [$validated['startDate'], $validated['endDate']]);
            })
            ->exists();

        if ($overlap) {
            return response()->json(['error' => 'Ez az időszak már foglalt!'], 400);
        }

        
        $Berlesek = Berlesek::create($validated);
        return response()->json($Berlesek, 201);
    }

    
    public function destroy($id)
    {
        $Berlesek = Berlesek::findOrFail($id);
        $Berlesek->delete();
        return response()->json(['message' => 'Bérlés törölve.'], 200);
    }

    
    public function update(Request $request, $id)
    {
        $validated = $request->validate([
            'uid' => 'integer',
            'yachtId' => 'integer',
            'startDate' => 'date|after:tomorrow',
            'endDate' => 'date|after_or_equal:startDate',
            'dailyPrice' => 'integer|min:1',
            'deposit' => 'integer|min:0'
        ]);

        $Berlesek = Berlesek::findOrFail($id);
        $Berlesek->update($validated);
        return response()->json($Berlesek, 200);
    }

}
