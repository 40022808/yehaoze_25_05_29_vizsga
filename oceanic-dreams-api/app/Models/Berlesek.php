<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Berlesek extends Model
{
     protected $fillable = [
        'uid', 'yachtId', 'startDate', 'endDate', 'dailyPrice', 'deposit'
    ];

    public function getTotalPriceAttribute()
    {
        return ($this->endDate->diffInDays($this->startDate) + 1) * $this->dailyPrice;
    }
}
